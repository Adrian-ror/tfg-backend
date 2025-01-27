package es.udc.tfg.tfgprojectbackend.model.entities;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import es.udc.tfg.tfgprojectbackend.model.exceptions.MaxWishListItemsExceededException;
import jakarta.persistence.*;

/**
 * Represents a wishlist for a user, containing a set of items.
 */
@Entity
public class WishList {

    public static final int MAX_ITEMS = 50; // Maximum number of items allowed in the wishlist.

    private Long id;
    private User user;
    private Set<WishListItem> items = new HashSet<>();

    public WishList() {}

    public WishList(User user) {
        this.user = user;
    }

    /**
     * Gets the unique identifier of the wishlist.
     *
     * @return the unique identifier of the wishlist
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user who owns the wishlist.
     *
     * @return the user associated with the wishlist
     */
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the items included in the wishlist.
     *
     * @return a set of items in the wishlist
     */
    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<WishListItem> getItems() {
        return items;
    }

    public void setItems(Set<WishListItem> items) {
        this.items = items;
    }

    /**
     * Adds an item to the wishlist if it does not exceed the maximum allowed items.
     *
     * @param item the item to add
     * @throws MaxWishListItemsExceededException if adding the item exceeds the maximum limit
     */
    public void addItem(WishListItem item) throws MaxWishListItemsExceededException {
        if (items.size() >= MAX_ITEMS) {
            throw new MaxWishListItemsExceededException(MAX_ITEMS);
        }
        items.add(item);
        item.setWishList(this); // Associate the wishlist with the item.
    }

    /**
     * Retrieves a WishListItem by product ID.
     *
     * @param productId the ID of the product to search for
     * @return an Optional containing the WishListItem if found, or empty if not found
     */
    @Transient
    public Optional<WishListItem> getItem(Long productId) {
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();
    }


    /**
     * Removes all items from the wishlist.
     */
    public void removeAllItems() {
        items.clear();
    }

    /**
     * Removes a specific item from the wishlist.
     *
     * @param existingListItem the item to remove
     */
    public void removeItem(WishListItem existingListItem) {
        items.remove(existingListItem);
    }

    /**
     * Checks if the wishlist is empty.
     *
     * @return true if the wishlist is empty, false otherwise
     */
    @Transient
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
