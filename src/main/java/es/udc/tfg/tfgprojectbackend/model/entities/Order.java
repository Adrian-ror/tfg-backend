package es.udc.tfg.tfgprojectbackend.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import es.udc.tfg.tfgprojectbackend.model.exceptions.MaxOrderItemsExceededException;
import jakarta.persistence.*;

/**
 * Represents an order made by a user, containing a collection of order items,
 * payment method, shipping method, and other relevant information.
 */
@Entity
@Table(name = "OrderTable")
public class Order {

    /**
     * Enumeration representing the possible states of an order.
     */
    public enum OrderState {
        PRE_ORDER,
        IN_TRANSIT,
        CONFIRMED,
        CANCELLED
    }

    /** Maximum number of items allowed in an order. */
    public static final int MAX_ITEMS = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items = new HashSet<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "paymentMethodId")
    private PaymentMethod paymentMethod;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shippingMethodId")
    private ShippingMethod shippingMethod;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userAddressId")
    private UserAddress userAddress;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderState state;

    /**
     * Default constructor for Order.
     */
    public Order() {}

    /**
     * Constructs an Order with specified user, payment method, shipping method,
     * user address, date, and state.
     *
     * @param user the user associated with this order
     * @param paymentMethod the payment method used for this order
     * @param shippingMethod the shipping method for this order
     * @param userAddress the address to which the order will be shipped
     * @param date the date when the order was created
     * @param state the current state of the order
     */
    public Order(User user, PaymentMethod paymentMethod, ShippingMethod shippingMethod, UserAddress userAddress,
                 LocalDateTime date, OrderState state) {
        this.user = user;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.userAddress = userAddress;
        this.date = date;
        this.state = state;
    }

    /**
     * Gets the unique identifier for this order.
     *
     * @return the ID of this order
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this order.
     *
     * @param id the ID to set for this order
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the set of items associated with this order.
     *
     * @return the set of order items
     */
    public Set<OrderItem> getItems() {
        return items;
    }

    /**
     * Sets the set of items associated with this order.
     *
     * @param items the set of order items to set
     */
    public void setItems(Set<OrderItem> items) {
        this.items = items;
    }

    /**
     * Gets the user who made this order.
     *
     * @return the associated user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who made this order.
     *
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the payment method used for this order.
     *
     * @return the associated payment method
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method used for this order.
     *
     * @param paymentMethod the payment method to set
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Gets the shipping method for this order.
     *
     * @return the associated shipping method
     */
    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Sets the shipping method for this order.
     *
     * @param shippingMethod the shipping method to set
     */
    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    /**
     * Gets the user address associated with this order.
     *
     * @return the associated user address
     */
    public UserAddress getUserAddress() {
        return userAddress;
    }

    /**
     * Sets the user address associated with this order.
     *
     * @param userAddress the user address to set
     */
    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * Gets the date when this order was created.
     *
     * @return the date of the order
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date when this order was created.
     *
     * @param date the date to set for this order
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Gets the current state of this order.
     *
     * @return the state of the order
     */
    public OrderState getState() {
        return state;
    }

    /**
     * Sets the current state of this order.
     *
     * @param state the state to set for this order
     */
    public void setState(OrderState state) {
        this.state = state;
    }

    /**
     * Retrieves an order item based on its product ID.
     *
     * @param productId the ID of the product to search for
     * @return an Optional containing the order item if found, or empty if not
     */
    @Transient
    public Optional<OrderItem> getItem(Long productId) {
        return items.stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();
    }

    /**
     * Adds an item to this order. If the maximum number of items is exceeded,
     * a MaxOrderItemsExceededException is thrown.
     *
     * @param item the order item to add
     * @throws MaxOrderItemsExceededException if adding the item exceeds the maximum allowed items
     */
    public void addItem(OrderItem item) throws MaxOrderItemsExceededException {
        if (items.size() >= MAX_ITEMS) {
            throw new MaxOrderItemsExceededException(MAX_ITEMS);
        }
        items.add(item);
        item.setOrder(this);
    }

    /**
     * Calculates the total price of all items in this order.
     *
     * @return the total price of the order
     */
    @Transient
    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
