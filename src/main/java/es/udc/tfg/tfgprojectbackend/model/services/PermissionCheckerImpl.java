package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementation of the PermissionChecker interface for verifying user existence, roles,
 * and ownership of specific entities such as shopping carts, orders, and wish lists.
 */
@Service
@Transactional(readOnly = true)
public class PermissionCheckerImpl implements PermissionChecker {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private WishListDao wishListDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the user ID
     * @throws InstanceNotFoundException if the user does not exist
     */
    @Override
    public void checkUserExists(Long userId) throws InstanceNotFoundException {
        if (!userDao.existsById(userId)) {
            throw new InstanceNotFoundException("project.entities.user", userId);
        }
    }

    @Override
    public Product checkProductExistsAndBelongsTo(Long productId, Long userId) throws PermissionException, InstanceNotFoundException {

        Product product = productDao.findById(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        if (!product.getUser().getId().equals(userId)) {
            throw new PermissionException();
        }

        return product;
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId the user ID
     * @return the User entity
     * @throws InstanceNotFoundException if the user does not exist
     */
    @Override
    public User checkUser(Long userId) throws InstanceNotFoundException {
        return userDao.findById(userId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.user", userId));
    }

    /**
     * Checks if a user has the required role.
     *
     * @param userId the user ID
     * @param role   the required role
     * @throws PermissionException       if the user does not have the required role
     * @throws InstanceNotFoundException if the user does not exist
     */
    @Override
    public void checkUserPermission(Long userId, User.RoleType role) throws PermissionException, InstanceNotFoundException {
        User user = checkUser(userId);
        if (!user.getRole().equals(role)) {
            throw new PermissionException();
        }
    }

    /**
     * Verifies that a shopping cart exists and belongs to a specific user.
     *
     * @param shoppingCartId the shopping cart ID
     * @param userId         the user ID
     * @return the ShoppingCart entity
     * @throws PermissionException       if the shopping cart does not belong to the user
     * @throws InstanceNotFoundException if the shopping cart does not exist
     */
    @Override
    public ShoppingCart checkShoppingCartExistsAndBelongsTo(Long shoppingCartId, Long userId)
            throws PermissionException, InstanceNotFoundException {
        ShoppingCart shoppingCart = shoppingCartDao.findById(shoppingCartId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.shoppingCart", shoppingCartId));

        if (!shoppingCart.getUser().getId().equals(userId)) {
            throw new PermissionException();
        }

        return shoppingCart;
    }

    /**
     * Verifies that an order exists and belongs to a specific user.
     *
     * @param orderId the order ID
     * @param userId  the user ID
     * @return the Order entity
     * @throws PermissionException       if the order does not belong to the user
     * @throws InstanceNotFoundException if the order does not exist
     */
    @Override
    public Order checkOrderExistsAndBelongsTo(Long orderId, Long userId)
            throws PermissionException, InstanceNotFoundException {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.order", orderId));

        if (!order.getUser().getId().equals(userId)) {
            throw new PermissionException();
        }

        return order;
    }

    /**
     * Verifies that a wish list exists and belongs to a specific user.
     *
     * @param wishListId the wish list ID
     * @param userId     the user ID
     * @return the WishList entity
     * @throws PermissionException       if the wish list does not belong to the user
     * @throws InstanceNotFoundException if the wish list does not exist
     */
    @Override
    public WishList checkWishListExistsAndBelongsTo(Long wishListId, Long userId)
            throws PermissionException, InstanceNotFoundException {
        WishList wishList = wishListDao.findById(wishListId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.wishList", wishListId));

        if (!wishList.getUser().getId().equals(userId)) {
            throw new PermissionException();
        }

        return wishList;
    }

    @Override
    public void checkUserCanReviewProduct(Long userId, Long productId) throws PermissionException, InstanceNotFoundException {

        checkUserExists(userId);

        productDao.findById(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        boolean hasPurchased = orderDao.existsByUserIdAndProductId(userId, productId);

        if (!hasPurchased) {
            throw new PermissionException();
        }

        checkUserPermission(userId, User.RoleType.CLIENT);
    }
}
