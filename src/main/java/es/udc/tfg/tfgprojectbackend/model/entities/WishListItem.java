package es.udc.tfg.tfgprojectbackend.model.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

/**
 * Represents an item in a user's wishlist.
 */
@Entity
public class WishListItem {

    private Long id;
    private Product product;
    private WishList wishList;
    private LocalDateTime addedDate;

    public WishListItem() {}

    public WishListItem(Product product, WishList wishlist, LocalDateTime addedDate) {
        this.product = product;
        this.wishList = wishlist;
        this.addedDate = addedDate;
    }

    /**
     * Gets the unique identifier of the wishlist item.
     *
     * @return the unique identifier of the wishlist item
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
     * Gets the product associated with this wishlist item.
     *
     * @return the product of the wishlist item
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets the wishlist that contains this item.
     *
     * @return the wishlist associated with this item
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlistId")
    public WishList getWishList() {
        return wishList;
    }

    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }

    /**
     * Gets the date when this item was added to the wishlist.
     *
     * @return the date when the item was added
     */
    public LocalDateTime getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDateTime addedDate) {
        this.addedDate = addedDate;
    }
}
