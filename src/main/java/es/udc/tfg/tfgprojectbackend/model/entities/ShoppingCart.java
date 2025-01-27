package es.udc.tfg.tfgprojectbackend.model.entities;

import es.udc.tfg.tfgprojectbackend.model.exceptions.MaxItemsExceededException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import jakarta.persistence.*;

/**
 * ShoppingCart entity class representing a user's shopping cart.
 * It allows the management of items and calculations related to the cart.
 * @version 0.1.0
 */
@Entity
public class ShoppingCart {

    public static final int MAX_ITEMS = 20;

    private Long id;
    private User user;
    private Set<ShoppingCartItem> items = new HashSet<>();

    /**
     * Default constructor.
     */
    public ShoppingCart() {}

    /**
     * Constructor with parameters.
     * @param user the user associated with the shopping cart.
     */
    public ShoppingCart(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(Set<ShoppingCartItem> items) {
        this.items = items;
    }

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Retrieves an item from the shopping cart based on the product ID.
     * @param productId the ID of the product.
     * @return an Optional containing the ShoppingCartItem if found.
     */
    @Transient
    public Optional<ShoppingCartItem> getItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }

    /**
     * Adds an item to the shopping cart.
     * @param item the ShoppingCartItem to add.
     * @throws MaxItemsExceededException if the cart already contains the maximum number of items.
     */
    public void addItem(ShoppingCartItem item) throws MaxItemsExceededException {
        if (items.size() >= MAX_ITEMS) {
            throw new MaxItemsExceededException(MAX_ITEMS);
        }
        items.add(item);
        item.setShoppingCart(this);
    }

    /**
     * Removes all items from the shopping cart.
     */
    public void removeAll() {
        items.clear();
    }

    /**
     * Removes a specific item from the shopping cart.
     * @param existingCartItem the ShoppingCartItem to remove.
     */
    public void removeItem(ShoppingCartItem existingCartItem) {
        items.remove(existingCartItem);
    }

    /**
     * Calculates the total quantity of items in the shopping cart.
     * @return the total quantity of items.
     */
    @Transient
    public int getTotalQuantity() {
        return items.stream()
                .mapToInt(ShoppingCartItem::getQuantity)
                .sum();
    }

    /**
     * Calculates the total price of items in the shopping cart.
     * @return the total price as a BigDecimal.
     */
    @Transient
    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(ShoppingCartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Checks if the shopping cart is empty.
     * @return true if the shopping cart is empty, false otherwise.
     */
    @Transient
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
