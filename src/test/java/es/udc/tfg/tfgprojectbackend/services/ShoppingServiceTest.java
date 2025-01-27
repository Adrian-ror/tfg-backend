package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.SellerService;
import es.udc.tfg.tfgprojectbackend.model.services.ShoppingService;
import es.udc.tfg.tfgprojectbackend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ShoppingServiceTest {

    private final Long NON_EXISTENT_ID = -1L;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private ShippingMethodDao shippingMethodDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingService shoppingService;

    private User createUser(String userName, User.RoleType role, User.StatusType status) {
        return new User(userName, "password", "FirstName", "LastName",
                userName + "@example.com", "123456789", null, role, status);
    }

    private String[] createSampleImages() {
        return new String[]{"image1_main.jpg", "image2.jpg"};
    }
    private Category createCategory() throws Exception {
        return (categoryDao.save(new Category("newCategory")));
    }


    private Product createSampleProduct(Long userId, Long categoryId) throws Exception {
        return sellerService.post(userId, "Product Name", "Full Description", "Short Description",
                new BigDecimal("100.00"), BigDecimal.ZERO, 10, BigDecimal.ZERO,
                false, "Brand", new BigDecimal("10.0"), new BigDecimal("5.0"),
                new BigDecimal("3.0"), new BigDecimal("1.0"), categoryId, createSampleImages());
    }

    private PaymentMethod createPaymentMethod(User user) {

        PaymentMethod paymentMethod = new PaymentMethod(
                user,
                "stripe_1234567890",
                "Visa",
                "US",
                12,
                2025,
                "1234",
                "credit",
                "fingerprint_abc123",
                true
        );

        return paymentMethodDao.save(paymentMethod);
    }

    private ShippingMethod createShippingMethod() {
        ShippingMethod method =  new ShippingMethod(
                "Standard Shipping",
                "Delivery within 5-7 business days",
                new BigDecimal("5.99"),
                "5-7 business days"
        );
        return shippingMethodDao.save(method);
    }

    private UserAddress createUserAddress(User user) {

        UserAddress userAddress  = new UserAddress(
                user,
                "123 Main St",
                "Apt 4B",
                "Test City",
                "Test State",
                "12345",
                "Test Country",
                "123-456-7890",
                true
        );

        return userAddressDao.save(userAddress);
    }


    private Order createOrder(User user, PaymentMethod method, ShippingMethod shipMethod, UserAddress address, Integer quantity, Product product) throws MaxOrderItemsExceededException {
        Order order = new Order(user, method, shipMethod, address, LocalDateTime.now(),Order.OrderState.PRE_ORDER);
        orderDao.save(order);

        OrderItem orderItem = new OrderItem(product, new BigDecimal("100.0"), quantity);
        order.addItem(orderItem);
        orderItemDao.save(orderItem);
        return order;
    }

    @Test
    public void testAddToShoppingCart() throws Exception {

        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        Category category = createCategory();

        Product product = createSampleProduct(client.getId(), category.getId());

        shoppingService.addToShoppingCart(client.getId(), client.getShoppingCart().getId(), product.getId(), 1);

        assertFalse(client.getShoppingCart().getItems().isEmpty());
    }

    @Test
    public void testUpdateShoppingCartItemQuantity() throws Exception {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        Category category = createCategory();

        Product product = createSampleProduct(client.getId(), category.getId());

        shoppingService.addToShoppingCart(client.getId(), client.getShoppingCart().getId(), product.getId(), 1);
        shoppingService.updateShoppingCartItemQuantity(client.getId(), client.getShoppingCart().getId(), product.getId(), 2);

        ShoppingCartItem updatedItem = client.getShoppingCart().getItem(product.getId()).get();
        assertEquals(2, updatedItem.getQuantity());
    }

    @Test
    public void testRemoveShoppingCartItem() throws Exception {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        Category category = createCategory();

        Product product = createSampleProduct(client.getId(), category.getId());

        shoppingService.addToShoppingCart(client.getId(), client.getShoppingCart().getId(), product.getId(), 1);
        shoppingService.removeShoppingCartItem(client.getId(), client.getShoppingCart().getId(), product.getId());

        assertFalse(client.getShoppingCart().getItems().contains(product));
    }

    @Test
    public void testBuy() throws Exception {


        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        Category category = createCategory();

        Product product = createSampleProduct(client.getId(), category.getId());

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);

        shoppingService.addToShoppingCart(client.getId(), client.getShoppingCart().getId(), product.getId(), 1);
        Order order = shoppingService.buy(client.getId(), client.getShoppingCart().getId(), paymentMethod.getId(), address.getId(), shippingMethod.getId());

        assertNotNull(order);
        assertEquals(1, order.getItems().size());
    }

    @Test
    public void testAddToWishList() throws Exception {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        Category category = createCategory();

        Product product = createSampleProduct(client.getId(), category.getId());

        shoppingService.addToWishList(client.getId(), client.getWishList().getId(), product.getId());

        assertFalse(client.getWishList().getItems().isEmpty());
    }

    @Test
    public void testRemoveWishListItem() throws Exception {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        Category category = createCategory();

        Product product = createSampleProduct(client.getId(), category.getId());

        shoppingService.addToWishList(client.getId(), client.getWishList().getId(), product.getId());
        shoppingService.removeWishListItem(client.getId(), client.getWishList().getId(), product.getId());

        assertFalse(client.getWishList().getItems().contains(product));
    }

    @Test
    public void testAddProductReview() throws Exception {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);

        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        Category category = createCategory();

        Product product = createSampleProduct(provider.getId(), category.getId());

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);

        shoppingService.addToShoppingCart(client.getId(), client.getShoppingCart().getId(), product.getId(), 1);
        shoppingService.buy(client.getId(), client.getShoppingCart().getId(), paymentMethod.getId(), address.getId(), shippingMethod.getId());

        ProductReview review = shoppingService.addProductReview(client.getId(), product.getId(), 5, "Excellent product!");

        assertNotNull(review);
        assertEquals("Excellent product!", review.getComment());
        assertEquals(5, review.getRating().intValue());
    }

    @Test
    public void testIsReviewed() throws Exception {
        User client = createUser("client1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        Category category = createCategory();

        Product product = createSampleProduct(provider.getId(), category.getId());

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);

        shoppingService.addToShoppingCart(client.getId(), client.getShoppingCart().getId(), product.getId(), 1);
        shoppingService.buy(client.getId(), client.getShoppingCart().getId(), paymentMethod.getId(), address.getId(), shippingMethod.getId());


        shoppingService.addProductReview(client.getId(), product.getId(), 5, "Excellent product!");

        assertTrue(shoppingService.isReviewed(client.getId(), product.getId()));
    }

    @Test
    public void testIsProductPurchased() throws Exception {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        Category category = createCategory();

        Product product = createSampleProduct(client.getId(), category.getId());

        assertFalse(shoppingService.isProductPurchased(product.getId()));
    }

    @Test
    public void testFindAllShippingMethods() {

        createShippingMethod();

        List<ShippingMethod> shippingMethods = shoppingService.findAllShippingMethods();

        assertFalse(shippingMethods.isEmpty());
    }
}
