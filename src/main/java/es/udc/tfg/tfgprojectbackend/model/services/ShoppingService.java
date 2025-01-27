package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;

import java.util.List;

public interface ShoppingService {

    /**
     * Adds a product to the user's shopping cart.
     *
     * @param userId the ID of the user
     * @param shoppingCartId the ID of the shopping cart
     * @param productId the ID of the product to add
     * @param quantity the quantity of the product to add
     * @return the updated shopping cart
     * @throws InstanceNotFoundException if the user, shopping cart, or product does not exist
     * @throws PermissionException if the user does not have permission to modify the shopping cart
     * @throws MaxQuantityExceededException if the maximum quantity of the product is exceeded
     * @throws MaxItemsExceededException if the maximum number of items in the cart is exceeded
     */
    ShoppingCart addToShoppingCart(Long userId, Long shoppingCartId, Long productId, int quantity)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException;

    /**
     * Updates the quantity of an item in the shopping cart.
     *
     * @param userId the ID of the user
     * @param shoppingCartId the ID of the shopping cart
     * @param productId the ID of the product to update
     * @param quantity the new quantity of the product
     * @return the updated shopping cart
     * @throws InstanceNotFoundException if the user, shopping cart, or product does not exist
     * @throws PermissionException if the user does not have permission to modify the shopping cart
     * @throws MaxQuantityExceededException if the maximum quantity of the product is exceeded
     */
    ShoppingCart updateShoppingCartItemQuantity(Long userId, Long shoppingCartId, Long productId, int quantity)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException;

    /**
     * Removes an item from the shopping cart.
     *
     * @param userId the ID of the user
     * @param shoppingCartId the ID of the shopping cart
     * @param productId the ID of the product to remove
     * @return the updated shopping cart
     * @throws InstanceNotFoundException if the user, shopping cart, or product does not exist
     * @throws PermissionException if the user does not have permission to modify the shopping cart
     */
    ShoppingCart removeShoppingCartItem(Long userId, Long shoppingCartId, Long productId)
            throws InstanceNotFoundException, PermissionException;

    /**
     * Completes the purchase of the items in the shopping cart.
     *
     * @param userId the ID of the user
     * @param shoppingCartId the ID of the shopping cart
     * @param paymentMethodId the ID of the payment method to use
     * @param userAddressId the ID of the user's shipping address
     * @param shippingMethodId the ID of the shipping method
     * @return the created order
     * @throws InstanceNotFoundException if any of the involved entities do not exist
     * @throws PermissionException if the user does not have permission to complete the purchase
     * @throws EmptyShoppingCartException if the shopping cart is empty
     * @throws NotAssignedPaymentMethod if no payment method is assigned to the user
     * @throws MaxOrderItemsExceededException if the number of items in the order exceeds the allowed limit
     */
    Order buy(Long userId, Long shoppingCartId, Long paymentMethodId, Long userAddressId, Long shippingMethodId)
            throws InstanceNotFoundException, PermissionException, EmptyShoppingCartException, NotAssignedPaymentMethod, MaxOrderItemsExceededException;

    /**
     * Adds a product to the user's wish list.
     *
     * @param userId the ID of the user
     * @param productListId the ID of the wish list
     * @param productId the ID of the product to add
     * @return the updated wish list
     * @throws InstanceNotFoundException if the user, product list, or product does not exist
     * @throws PermissionException if the user does not have permission to modify the wish list
     * @throws MaxQuantityExceededException if the maximum quantity of the product is exceeded
     * @throws MaxItemsExceededException if the maximum number of items in the wish list is exceeded
     * @throws MaxWishListItemsExceededException if the number of items in the wish list exceeds the allowed limit
     */
    WishList addToWishList(Long userId, Long productListId, Long productId)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException, MaxWishListItemsExceededException;

    /**
     * Removes a product from the user's wish list.
     *
     * @param userId the ID of the user
     * @param productListId the ID of the wish list
     * @param productId the ID of the product to remove
     * @return the updated wish list
     * @throws InstanceNotFoundException if the user, product list, or product does not exist
     * @throws PermissionException if the user does not have permission to modify the wish list
     */
    WishList removeWishListItem(Long userId, Long productListId, Long productId)
            throws InstanceNotFoundException, PermissionException;

    /**
     * Adds a review for a product.
     *
     * @param userId the ID of the user adding the review
     * @param productId the ID of the product being reviewed
     * @param rating the rating given to the product
     * @param comment the comment provided with the review
     * @return the created product review
     * @throws InstanceNotFoundException if the user or product does not exist
     * @throws PermissionException if the user does not have permission to review the product
     */
    ProductReview addProductReview(Long userId, Long productId, int rating, String comment)
            throws InstanceNotFoundException, PermissionException;

    /**
     * Retrieves all reviews for a product.
     *
     * @param productId the ID of the product
     * @return a list of product reviews
     * @throws InstanceNotFoundException if the product does not exist
     */
    List<ProductReview> getProductReviews(Long productId) throws InstanceNotFoundException;

    /**
     * Checks if the user has already reviewed a product.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product
     * @return true if the user has reviewed the product, false otherwise
     * @throws InstanceNotFoundException if the user or product does not exist
     * @throws PermissionException if the user does not have permission to check the review status
     */
    Boolean isReviewed(Long userId, Long productId)
            throws InstanceNotFoundException, PermissionException;

    /**
     * Checks if a product has been purchased.
     *
     * @param productId the ID of the product to check
     * @return true if the product has been purchased, false otherwise
     */
    boolean isProductPurchased(Long productId);

    /**
     * Retrieves all available shipping methods.
     *
     * @return a list of shipping methods
     */
    List<ShippingMethod> findAllShippingMethods();
}
