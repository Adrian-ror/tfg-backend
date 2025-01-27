package es.udc.tfg.tfgprojectbackend.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import jakarta.persistence.*;

/**
 * Represents an item in an order, including the associated product,
 * product price, and quantity of the product ordered.
 */
@Entity
public class OrderItem {

    private Long id;
    private Product product;
    private Order order;
    private BigDecimal productPrice;
    private int quantity;

    /**
     * Default constructor for OrderItem.
     */
    public OrderItem() {}

    /**
     * Constructs an OrderItem with specified product, product price, and quantity.
     *
     * @param product the product associated with this order item
     * @param productPrice the price of the product
     * @param quantity the quantity of the product ordered
     */
    public OrderItem(Product product, BigDecimal productPrice, int quantity) {
        this.product = product;
        this.productPrice = productPrice.setScale(2, RoundingMode.HALF_EVEN);
        this.quantity = quantity;
    }

    /**
     * Gets the unique identifier for this order item.
     *
     * @return the ID of this order item
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this order item.
     *
     * @param id the ID to set for this order item
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the product associated with this order item.
     *
     * @return the associated product
     */
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="productId")
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with this order item.
     *
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets the order that contains this order item.
     *
     * @return the associated order
     */
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="orderId")
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the order that contains this order item.
     *
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Gets the quantity of the product ordered.
     *
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product ordered.
     *
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the product.
     *
     * @return the price of the product
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * Sets the price of the product.
     *
     * @param productPrice the price to set
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Calculates the total price for this order item based on the product price and quantity.
     *
     * @return the total price of this order item
     */
    @Transient
    public BigDecimal getTotalPrice() {
        return productPrice.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
