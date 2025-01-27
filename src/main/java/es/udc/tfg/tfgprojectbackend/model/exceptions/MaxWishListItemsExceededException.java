package es.udc.tfg.tfgprojectbackend.model.exceptions;

/**
 * Exception thrown when the maximum number of items in a wishlist is exceeded.
 */
@SuppressWarnings("serial")
public class MaxWishListItemsExceededException extends Exception {

    private int maxAllowedItems;

    public MaxWishListItemsExceededException(int maxAllowedItems) {
        this.maxAllowedItems = maxAllowedItems;
    }

    public int getMaxAllowedItems() {
        return maxAllowedItems;
    }
}
