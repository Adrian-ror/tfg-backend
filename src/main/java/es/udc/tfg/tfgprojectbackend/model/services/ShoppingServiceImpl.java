package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ShoppingServiceImpl implements ShoppingService {

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private ShippingMethodDao shippingMethodDao;

    @Autowired
    private ShoppingCartItemDao shoppingCartItemDao;

    @Autowired
    private WishListItemDao wishListItemDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductReviewDao productReviewDao;


    // Métodos de la interfaz ShoppingService

    @Override
    public ShoppingCart addToShoppingCart(Long userId, Long shoppingCartId, Long productId, int quantity)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException {

        ShoppingCart shoppingCart = permissionChecker.checkShoppingCartExistsAndBelongsTo(shoppingCartId, userId);
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        if(!product.getIsVisible()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        Optional<ShoppingCartItem> existingCartItem = shoppingCart.getItem(productId);

        if (existingCartItem.isPresent()) {
            existingCartItem.get().incrementQuantity(quantity);
        } else {
            ShoppingCartItem newCartItem = new ShoppingCartItem(product, shoppingCart, quantity);
            shoppingCart.addItem(newCartItem);
            shoppingCartItemDao.save(newCartItem);
        }

        return shoppingCart;
    }

    @Override
    public ShoppingCart updateShoppingCartItemQuantity(Long userId, Long shoppingCartId, Long productId, int quantity)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException {

        ShoppingCart shoppingCart = permissionChecker.checkShoppingCartExistsAndBelongsTo(shoppingCartId, userId);
        ShoppingCartItem existingCartItem = shoppingCart.getItem(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        existingCartItem.setQuantity(quantity);
        return shoppingCart;
    }

    @Override
    public ShoppingCart removeShoppingCartItem(Long userId, Long shoppingCartId, Long productId)
            throws InstanceNotFoundException, PermissionException {

        ShoppingCart shoppingCart = permissionChecker.checkShoppingCartExistsAndBelongsTo(shoppingCartId, userId);
        ShoppingCartItem existingCartItem = shoppingCart.getItem(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        shoppingCart.removeItem(existingCartItem);
        shoppingCartItemDao.delete(existingCartItem);
        return shoppingCart;
    }

    @Override
    public Order buy(Long userId, Long shoppingCartId, Long paymentMethodId, Long userAddressId, Long shippingMethodId)
            throws InstanceNotFoundException, PermissionException, EmptyShoppingCartException, MaxOrderItemsExceededException {

        ShoppingCart shoppingCart = permissionChecker.checkShoppingCartExistsAndBelongsTo(shoppingCartId, userId);

        if (shoppingCart.isEmpty()) {
            throw new EmptyShoppingCartException();
        }

        PaymentMethod paymentMethod = paymentMethodDao.findByUserIdAndId(userId, paymentMethodId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.paymentMethod", paymentMethodId));

        UserAddress userAddress = userAddressDao.findByUserIdAndId(userId, userAddressId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.userAddress", userAddressId));

        ShippingMethod shippingMethod = shippingMethodDao.findById(shippingMethodId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.shippingMethod", shippingMethodId));

        Order order = new Order(shoppingCart.getUser(), paymentMethod, shippingMethod, userAddress, LocalDateTime.now(), Order.OrderState.PRE_ORDER);
        orderDao.save(order);

        for (ShoppingCartItem shoppingCartItem : shoppingCart.getItems()) {
            OrderItem orderItem = new OrderItem(shoppingCartItem.getProduct(),
                    shoppingCartItem.getProduct().getPrice(), shoppingCartItem.getQuantity());

            order.addItem(orderItem);
            orderItemDao.save(orderItem);
            orderItem.getProduct().updateStock(shoppingCartItem.getQuantity());
            shoppingCartItemDao.delete(shoppingCartItem);
        }

        shoppingCart.removeAll();
        return order;
    }

    @Override
    public WishList addToWishList(Long userId, Long productListId, Long productId)
            throws InstanceNotFoundException, PermissionException, MaxWishListItemsExceededException {

        WishList wishList = permissionChecker.checkWishListExistsAndBelongsTo(productListId, userId);
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        if(!product.getIsVisible()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        if (wishList.getItem(productId).isEmpty()) {
            WishListItem newListItem = new WishListItem(product, wishList, LocalDateTime.now());
            wishList.addItem(newListItem);
            wishListItemDao.save(newListItem);
        }

        return wishList;
    }

    @Override
    public WishList removeWishListItem(Long userId, Long productListId, Long productId)
            throws InstanceNotFoundException, PermissionException {

        WishList wishList = permissionChecker.checkWishListExistsAndBelongsTo(productListId, userId);
        WishListItem existingListItem = wishList.getItem(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        wishList.removeItem(existingListItem);
        wishListItemDao.delete(existingListItem);
        return wishList;
    }

    @Override
    public ProductReview addProductReview(Long userId, Long productId, int rating, String comment)
            throws InstanceNotFoundException, PermissionException {

        permissionChecker.checkUserCanReviewProduct(userId, productId);
        User user = permissionChecker.checkUser(userId);

        Product product = productDao.findById(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        if(!product.getIsVisible()){
            throw new InstanceNotFoundException("project.entities.product", productId);
        }

        ProductReview productReview = new ProductReview(product, user, BigDecimal.valueOf(rating), comment, LocalDateTime.now());
        productReviewDao.save(productReview);
        product.addReview(productReview);
        return productReview;
    }

    @Override
    public List<ProductReview> getProductReviews(Long productId)
            throws InstanceNotFoundException {

        Product product = productDao.findById(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        return productReviewDao.findProductReviewByProductId(productId);
    }

    @Override
    public Boolean isReviewed(Long userId, Long productId)
            throws InstanceNotFoundException{

        permissionChecker.checkUser(userId);

        productDao.findById(productId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", productId));

        return productReviewDao.existsProductReviewByUserIdAndProductId(userId, productId);
    }

    /**
     * Checks if a product has been purchased.
     *
     * @param productId the ID of the product to check
     * @return true if the product has been purchased, false otherwise
     */
    @Override
    public boolean isProductPurchased(Long productId) {
        return orderItemDao.existsOrderItemByProductId(productId);
    }



    @Override
    public List<ShippingMethod> findAllShippingMethods() {
        return StreamSupport.stream(shippingMethodDao.findAll().spliterator(), false)
                .sorted(Comparator.comparing(ShippingMethod::getName))
                .collect(Collectors.toList());
    }

}
