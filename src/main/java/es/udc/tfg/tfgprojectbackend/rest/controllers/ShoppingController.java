package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.ProductReview;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.ShoppingService;
import es.udc.tfg.tfgprojectbackend.rest.common.ErrorsDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.ProductConversor.toProductReviewDto;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.ProductConversor.toProductReviewDtos;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.ShippingMethodConversor.toShippingMethodDtos;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.ShoppingCartConversor.toShoppingCartDto;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.WishListConversor.toWishListDto;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private final static String MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE = "project.exceptions.MaxQuantityExceededException";
    private final static String MAX_ITEMS_EXCEEDED_EXCEPTION_CODE = "project.exceptions.MaxItemsExceededException";
    private final static String EMPTY_SHOPPING_CART_EXCEPTION_CODE = "project.exceptions.EmptyShoppingCartException";
    private final static String NOT_ASSIGNED_PAYMENT_METHOD_CODE = "project.exceptions.NotAssignedPaymentMethod";
    private final static String MAX_WISHLIST_ITEMS_EXCEEDED_EXCEPTION_CODE = "project.exceptions.MaxWishListItemsExceededException";
    private final static String MAX_ORDER_ITEMS_EXCEEDED_EXCEPTION_CODE = "project.exceptions.MaxOrderItemsExceededException";


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ShoppingService shoppingService;


    @ExceptionHandler(MaxQuantityExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxQuantityExceededException(MaxQuantityExceededException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE,
                new Object[]{exception.getMaxAllowedIncrement()}, MAX_QUANTITY_EXCEEDED_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(MaxItemsExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxItemsExceededException(MaxItemsExceededException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MAX_ITEMS_EXCEEDED_EXCEPTION_CODE, new Object[]{exception.getMaxAllowedItems()},
                MAX_ITEMS_EXCEEDED_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(EmptyShoppingCartException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleEmptyShoppingCartException(EmptyShoppingCartException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(EMPTY_SHOPPING_CART_EXCEPTION_CODE, null,
                EMPTY_SHOPPING_CART_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }


    @ExceptionHandler(NotAssignedPaymentMethod.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleNotAssignedPaymentMethod(NotAssignedPaymentMethod exception, Locale locale) {
        String errorMessage = messageSource.getMessage(NOT_ASSIGNED_PAYMENT_METHOD_CODE, null,
                NOT_ASSIGNED_PAYMENT_METHOD_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(MaxWishListItemsExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxWishListItemsExceededException(MaxWishListItemsExceededException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MAX_WISHLIST_ITEMS_EXCEEDED_EXCEPTION_CODE, new Object[]{exception.getMaxAllowedItems()},
                MAX_WISHLIST_ITEMS_EXCEEDED_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(MaxOrderItemsExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxOrderItemsExceededException(MaxOrderItemsExceededException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MAX_ORDER_ITEMS_EXCEEDED_EXCEPTION_CODE,  new Object[]{exception.getMaxAllowedItems()},
                MAX_ORDER_ITEMS_EXCEEDED_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }



    @PostMapping("/shopping-carts/{shoppingCartId}/add")
    public ShoppingCartDto addToShoppingCart(@RequestAttribute Long userId, @PathVariable Long shoppingCartId,
                                             @Validated @RequestBody AddToShoppingCartParamsDto params)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException {
        return toShoppingCartDto(shoppingService.addToShoppingCart(userId, shoppingCartId, params.getProductId(),
                params.getQuantity()));
    }

    @PostMapping("/shopping-carts/{shoppingCartId}/update")
    public ShoppingCartDto updateShoppingCartItemQuantity(@RequestAttribute Long userId,
                                                          @PathVariable Long shoppingCartId, @RequestBody UpdateShoppingCartItemQuantityParamsDto params)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException {
        return toShoppingCartDto(shoppingService.updateShoppingCartItemQuantity(userId, shoppingCartId,
                params.getProductId(), params.getQuantity()));
    }

    @PostMapping("/shopping-carts/{shoppingCartId}/remove")
    public ShoppingCartDto removeShoppingCartItem(@RequestAttribute Long userId, @PathVariable Long shoppingCartId,
                                                  @RequestBody RemoveShoppingCartItemParamsDto params) throws InstanceNotFoundException, PermissionException {
        return toShoppingCartDto(shoppingService.removeShoppingCartItem(userId, shoppingCartId, params.getProductId()));
    }




    @PostMapping("/shopping-carts/{shoppingCartId}/buy")
    public Long buy(@RequestAttribute Long userId, @PathVariable Long shoppingCartId,
                    @Validated @RequestBody BuyParamsDto params)
            throws InstanceNotFoundException, PermissionException, EmptyShoppingCartException, NotAssignedPaymentMethod, MaxOrderItemsExceededException {
        return shoppingService.buy(userId, shoppingCartId, params.getPaymentMethodId(), params.getUserAddressId(),
                params.getShippingMethodId()).getId();
    }




    @PostMapping("/wish-lists/{wishListId}/add")
    public WishListDto addToWishList(@RequestAttribute Long userId, @PathVariable Long wishListId,
                                        @Validated @RequestBody AddToWishListParamsDto params)
            throws InstanceNotFoundException, PermissionException, MaxQuantityExceededException, MaxItemsExceededException, MaxWishListItemsExceededException {
        return toWishListDto(shoppingService.addToWishList(userId, wishListId, params.getProductId()));
    }

    @PostMapping("/wish-lists/{wishListId}/remove")
    public WishListDto removeWishListItem(@RequestAttribute Long userId, @PathVariable Long wishListId,
                                             @RequestBody RemoveWishListItemParamsDto params) throws InstanceNotFoundException, PermissionException {
        return toWishListDto(shoppingService.removeWishListItem(userId, wishListId, params.getProductId()));
    }


    @GetMapping("/products/{productId}/reviews")
    public List<ProductReviewDto> getProductReviews(@PathVariable Long productId) throws InstanceNotFoundException {
        return toProductReviewDtos(shoppingService.getProductReviews(productId));
    }


    @PostMapping("/products/{productId}/review")
    public ProductReviewDto addProductReview(@RequestAttribute Long userId, @PathVariable Long productId,
                                             @Validated @RequestBody AddProductReviewParamsDto params)
            throws InstanceNotFoundException, PermissionException {
        return toProductReviewDto(shoppingService.addProductReview(userId, productId, params.getRating(), params.getComment()));
    }

    @GetMapping("/products/{productId}/is-reviewed")
    public boolean isReviewed(@RequestAttribute Long userId, @PathVariable Long productId) throws PermissionException, InstanceNotFoundException {
        return shoppingService.isReviewed(userId, productId);
    }

    /**
     * Checks if a product has been purchased.
     *
     * @param productId the ID of the product to check.
     * @return true if the product has been purchased, false otherwise.
     */
    @GetMapping("/products/{productId}/is-purchased")
    public boolean isProductPurchased(@PathVariable Long productId) {
        return shoppingService.isProductPurchased(productId);
    }


    @GetMapping("/shipping-methods")
    public List<ShippingMethodDto> getShippingMethods() {
        return toShippingMethodDtos(shoppingService.findAllShippingMethods());
    }

}
