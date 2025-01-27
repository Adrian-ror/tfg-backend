package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;

/**
 * Service interface to verify user existence, retrieve user details, and validate user permissions.
 */
public interface PermissionChecker {

    /**
     * Checks if a user exists.
     *
     * @param userId the ID of the user to check.
     * @throws InstanceNotFoundException if the user does not exist.
     */
    void checkUserExists(Long userId) throws InstanceNotFoundException;

    /**
     * Verifies if the product exists and belongs to the specified user.
     *
     * @param productId the ID of the product to check.
     * @param userId the ID of the user to verify ownership.
     * @return the Product entity if the product exists and belongs to the user.
     * @throws PermissionException if the product does not belong to the user.
     * @throws InstanceNotFoundException if the product does not exist.
     */
    Product checkProductExistsAndBelongsTo(Long productId, Long userId) throws PermissionException, InstanceNotFoundException;

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the ID of the user to retrieve.
     * @return the User entity.
     * @throws InstanceNotFoundException if the user does not exist.
     */
    User checkUser(Long userId) throws InstanceNotFoundException;

    /**
     * Verifies if the shopping cart exists and belongs to the specified user.
     *
     * @param shoppingCartId the ID of the shopping cart to check.
     * @param userId the ID of the user to verify ownership.
     * @return the ShoppingCart entity if it exists and belongs to the user.
     * @throws PermissionException if the shopping cart does not belong to the user.
     * @throws InstanceNotFoundException if the shopping cart does not exist.
     */
    ShoppingCart checkShoppingCartExistsAndBelongsTo(Long shoppingCartId, Long userId)
            throws PermissionException, InstanceNotFoundException;

    /**
     * Verifies if the order exists and belongs to the specified user.
     *
     * @param orderId the ID of the order to check.
     * @param userId the ID of the user to verify ownership.
     * @return the Order entity if it exists and belongs to the user.
     * @throws PermissionException if the order does not belong to the user.
     * @throws InstanceNotFoundException if the order does not exist.
     */
    Order checkOrderExistsAndBelongsTo(Long orderId, Long userId)
            throws PermissionException, InstanceNotFoundException;

    /**
     * Verifies if the wish list exists and belongs to the specified user.
     *
     * @param wishListId the ID of the wish list to check.
     * @param userId the ID of the user to verify ownership.
     * @return the WishList entity if it exists and belongs to the user.
     * @throws PermissionException if the wish list does not belong to the user.
     * @throws InstanceNotFoundException if the wish list does not exist.
     */
    WishList checkWishListExistsAndBelongsTo(Long wishListId, Long userId)
            throws PermissionException, InstanceNotFoundException;

    /**
     * Verifies if a user has the required permission to perform a specific action.
     *
     * @param userId the ID of the user to check.
     * @param role the required role type to perform the action.
     * @throws PermissionException if the user does not have the required permission.
     * @throws InstanceNotFoundException if the user does not exist.
     */
    void checkUserPermission(Long userId, User.RoleType role) throws PermissionException, InstanceNotFoundException;

    /**
     * Verifies if a user has permission to review a specific product.
     *
     * @param userId the ID of the user to check.
     * @param productId the ID of the product to check.
     * @throws PermissionException if the user does not have permission to review the product.
     * @throws InstanceNotFoundException if the user or product does not exist.
     */
    void checkUserCanReviewProduct(Long userId, Long productId) throws PermissionException, InstanceNotFoundException;
}

