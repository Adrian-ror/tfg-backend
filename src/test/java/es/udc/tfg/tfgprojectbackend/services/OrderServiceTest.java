package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.Block;
import es.udc.tfg.tfgprojectbackend.model.services.CatalogService;
import es.udc.tfg.tfgprojectbackend.model.services.OrderService;
import es.udc.tfg.tfgprojectbackend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class OrderServiceTest {

    private final Long NON_EXISTENT_ID = -1L;

    @Autowired
    private OrderService orderService;


    @Autowired
    private UserService userService;

    @Autowired
    private ProductDao productDao;

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

    private User createUser(String userName, User.RoleType role, User.StatusType status) {
        return new User(userName, "password", "FirstName", "LastName",
                userName + "@example.com", "123456789", null, role, status);
    }

    private Product createProduct(User user, String productName) {
        Category newCategory = categoryDao.save(new Category("Electronics"));

        Product newProduct = new Product(
                productName,
                "This is a detailed description of the product.",
                "Short description of the product.",
                new BigDecimal("19.99"),
                new BigDecimal("0.10"),
                100,
                new BigDecimal("0"),
                true,
                false,
                "BrandName",
                new BigDecimal("10.0"),
                new BigDecimal("5.0"),
                new BigDecimal("2.0"),
                new BigDecimal("0.5"),
                LocalDateTime.now(),
                newCategory,
                user
        );
        return productDao.save(newProduct);
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
    public void testCreateOrder() throws InstanceNotFoundException, DuplicateInstanceException, MaxOrderItemsExceededException, PermissionException {

        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);
        Product product = createProduct(client, "product");

        Order order = createOrder(client,paymentMethod,shippingMethod,address,4, product);

        Order createdOrder = orderService.findOrder(client.getId(), order.getId());
        assertEquals(order, createdOrder);
        assertEquals(Order.OrderState.PRE_ORDER, createdOrder.getState());
        assertEquals(1, createdOrder.getItems().size());
    }


    @Test
    public void testFindOrderById() throws InstanceNotFoundException, DuplicateInstanceException, MaxOrderItemsExceededException, PermissionException {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);
        Product product = createProduct(client, "product");

        Order order = createOrder(client,paymentMethod,shippingMethod,address,4, product);

        Order foundOrder = orderService.findOrder(client.getId(),order.getId());
        assertEquals(order, foundOrder);
        assertTrue(foundOrder.getItems().contains(order.getItems().stream().findFirst().get()));
    }

    @Test
    public void testFindOrderByIdWithNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> orderService.findOrder(NON_EXISTENT_ID, NON_EXISTENT_ID));
    }

    @Test
    public void testUpdateOrderStatus() throws InstanceNotFoundException, DuplicateInstanceException, MaxOrderItemsExceededException, PermissionException {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);
        Product product = createProduct(client, "product");

        Order order = createOrder(client,paymentMethod,shippingMethod,address,4, product);

        order.setState(Order.OrderState.CONFIRMED);
        orderService.changeOrderState(order.getId(), order.getState());

        Order updatedOrder = orderService.findOrder(client.getId(), order.getId());
        assertEquals(Order.OrderState.CONFIRMED, updatedOrder.getState());
    }

    @Test
    public void testUpdateOrderStatusWithNonExistentOrder() {
        assertThrows(InstanceNotFoundException.class, () ->
                orderService.changeOrderState(NON_EXISTENT_ID, Order.OrderState.CONFIRMED));
    }

    @Test
    public void testCancelOrder() throws InstanceNotFoundException, DuplicateInstanceException, MaxOrderItemsExceededException, PermissionException {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);
        Product product = createProduct(client, "product");

        Order order = createOrder(client,paymentMethod,shippingMethod,address,4, product);

        orderService.changeOrderState(order.getId(), Order.OrderState.CANCELLED);
        Order cancelledOrder = orderService.findOrder(client.getId(), order.getId());
        assertEquals(Order.OrderState.CANCELLED, cancelledOrder.getState());
    }

    @Test
    public void testCancelOrderWithNonExistentOrder() {
        assertThrows(InstanceNotFoundException.class, () -> orderService.changeOrderState(NON_EXISTENT_ID, Order.OrderState.CANCELLED));
    }

    @Test
    public void testFindAllOrdersByUser() throws InstanceNotFoundException, DuplicateInstanceException, MaxOrderItemsExceededException {

        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);
        Product product = createProduct(client, "product");

        Order order1 = createOrder(client,paymentMethod,shippingMethod,address,4, product);
        Order order2 = createOrder(client,paymentMethod,shippingMethod,address,7, product);


        Block<Order> orders = orderService.findOrders(client.getId(), 0, 6);
        assertEquals(2, orders.getItems().size());
        assertTrue(orders.getItems().contains(order1));
        assertTrue(orders.getItems().contains(order2));
    }


    @Test
    public void testFindAllOrders() throws InstanceNotFoundException, MaxOrderItemsExceededException, DuplicateInstanceException {
        User client = createUser("client", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(client);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);

        PaymentMethod paymentMethod = createPaymentMethod(client);
        ShippingMethod shippingMethod = createShippingMethod();
        UserAddress address = createUserAddress(client);
        Product product = createProduct(client, "product");

        Order order1 = createOrder(client,paymentMethod,shippingMethod,address,4, product);
        Order order2 = createOrder(client,paymentMethod,shippingMethod,address,7, product);

        Block<Order> orders = orderService.findAllOrders(0,6);
        assertEquals(2, orders.getItems().size());
        assertTrue(orders.getItems().contains(order1));
        assertTrue(orders.getItems().contains(order2));
    }

}
