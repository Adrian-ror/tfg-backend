package es.udc.tfg.tfgprojectbackend.model.entities;

import es.udc.tfg.tfgprojectbackend.model.exceptions.MaxQuantityExceededException;

import java.math.BigDecimal;

import jakarta.persistence.*;

/**
 * Represents an item in the shopping cart.
 */
@Entity
public class ShoppingCartItem {

    public static final int MAX_QUANTITY = 100;

    private Long id;
    private Product product;
    private ShoppingCart shoppingCart;
    private int quantity = 0;

    /**
     * Default constructor.
     */
    public ShoppingCartItem() {}

    /**
     * Constructor with parameters.
     *
     * @param product The product to be added.
     * @param shoppingCart The shopping cart to which this item belongs.
     * @param quantity The quantity of the product to add.
     * @throws MaxQuantityExceededException if the quantity exceeds the maximum allowed.
     */
    public ShoppingCartItem(Product product, ShoppingCart shoppingCart, int quantity) throws MaxQuantityExceededException {
        this.product = product;
        this.shoppingCart = shoppingCart;
        setQuantity(quantity);
    }

    /**
     * Gets the ID of the shopping cart item.
     *
     * @return The ID of the shopping cart item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the shopping cart item.
     *
     * @param id The ID of the shopping cart item.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the product associated with this shopping cart item.
     *
     * @return The associated product.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    public Product getProduct() {
        return product;
    }

    /**
     * Sets the product associated with this shopping cart item.
     *
     * @param product The product to associate.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets the shopping cart associated with this item.
     *
     * @return The associated shopping cart.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shoppingCartId")
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Sets the shopping cart associated with this item.
     *
     * @param shoppingCart The shopping cart to associate.
     */
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Gets the quantity of the product in the shopping cart.
     *
     * @return The quantity of the product.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product in the shopping cart.
     *
     * @param quantity The quantity to set.
     * @throws MaxQuantityExceededException if the quantity exceeds the maximum allowed.
     */
    public void setQuantity(int quantity) throws MaxQuantityExceededException {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    /**
     * Increments the quantity of the product in the shopping cart.
     *
     * @param increment The amount to increment the quantity by.
     * @throws MaxQuantityExceededException if the new quantity exceeds the maximum allowed.
     */
    public void incrementQuantity(int increment) throws MaxQuantityExceededException {
        validateQuantity(this.quantity + increment);
        this.quantity += increment;
    }

    /**
     * Validates that the specified quantity does not exceed the maximum allowed.
     *
     * @param quantity The quantity to validate.
     * @throws MaxQuantityExceededException if the quantity exceeds the maximum allowed.
     */
    private void validateQuantity(int quantity) throws MaxQuantityExceededException {
        if (quantity > MAX_QUANTITY) {
            throw new MaxQuantityExceededException(MAX_QUANTITY);
        }
    }

    /**
     * Gets the total price of this shopping cart item.
     *
     * @return The total price of the item.
     */
    @Transient
    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
