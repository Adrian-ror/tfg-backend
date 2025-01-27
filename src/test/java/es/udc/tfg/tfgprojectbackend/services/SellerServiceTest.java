package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.SellerService;
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
public class SellerServiceTest {

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
    public void testPostProduct() throws Exception {
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        Category category = createCategory();

        Product product = createSampleProduct(provider.getId(), category.getId());

        assertNotNull(product.getId());
        assertEquals("Product Name", product.getName());
        assertEquals(2, product.getImages().size());
    }

    @Test
    public void testPostProductWithNonExistentCategory() throws DuplicateInstanceException {
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        assertThrows(InstanceNotFoundException.class, () ->
                sellerService.post(provider.getId(), "Product Name", "Full Description", "Short Description",
                        new BigDecimal("100.00"), BigDecimal.ZERO, 10, BigDecimal.ZERO,
                        false, "Brand", new BigDecimal("10.0"), new BigDecimal("5.0"),
                        new BigDecimal("3.0"), new BigDecimal("1.0"), NON_EXISTENT_ID, createSampleImages()));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        Category category = createCategory();

        Product product = createSampleProduct(provider.getId(), category.getId());
        Product updatedProduct = sellerService.update(provider.getId(), product.getId(), "Updated Name", "Updated Description",
                "Updated Short Description", new BigDecimal("150.00"), BigDecimal.ZERO, 5, BigDecimal.ZERO,
                false, "Updated Brand", new BigDecimal("20.0"), new BigDecimal("10.0"),
                new BigDecimal("6.0"), new BigDecimal("2.0"), category.getId(), new String[]{"image5_main.jpg"});

        assertEquals("Updated Name", updatedProduct.getName());
        assertEquals(new BigDecimal("150.00"), updatedProduct.getPrice());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);
        Category category = createCategory();

        Product product = createSampleProduct(provider.getId(), category.getId());
        sellerService.delete(provider.getId(), product.getId());

        assertFalse(product.getIsVisible());
    }

    @Test
    public void testGetAllProducts() throws Exception {
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);
        Category category = createCategory();

        createSampleProduct(provider.getId(), category.getId());
        createSampleProduct(provider.getId(), category.getId());

        List<Product> products = sellerService.getAllProducts(provider.getId());

        assertEquals(2, products.size());
    }

    @Test
    public void testGetProductById() throws Exception {
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);
        Category category = createCategory();

        Product product = createSampleProduct(provider.getId(), category.getId());
        Product fetchedProduct = sellerService.getProductById(provider.getId(), product.getId());

        assertEquals(product.getId(), fetchedProduct.getId());
        assertEquals(product.getName(), fetchedProduct.getName());
    }


    @Test
    public void testGetAllSoldProducts() throws Exception {

        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        Category category = createCategory();

        Product product1 = createSampleProduct(provider.getId(), category.getId());
        Product product2 = createSampleProduct(provider.getId(), category.getId());

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);

        createOrder(client,paymentMethod,shippingMethod,address,4, product1);
        createOrder(client,paymentMethod,shippingMethod,address,4, product2);


        List<Product> soldProducts = sellerService.getAllSoldProducts(provider.getId());

        assertEquals(2, soldProducts.size());
        assertTrue(soldProducts.contains(product1));
        assertTrue(soldProducts.contains(product2));
    }

    @Test
    public void testGetAllSoldProductsWithNoSales() throws InstanceNotFoundException, DuplicateInstanceException {
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        List<Product> soldProducts = sellerService.getAllSoldProducts(provider.getId());

        assertTrue(soldProducts.isEmpty());
    }

    @Test
    public void testGetAllSoldProductsWithNonExistentUser() {
        assertThrows(InstanceNotFoundException.class, () -> sellerService.getAllSoldProducts(-1L));
    }
}
