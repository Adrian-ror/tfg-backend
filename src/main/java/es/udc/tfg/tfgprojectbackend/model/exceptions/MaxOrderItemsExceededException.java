package es.udc.tfg.tfgprojectbackend.model.exceptions;

/**
 * Exception thrown when the maximum number of items in a shopping cart is exceeded.
 */
@SuppressWarnings("serial")
public class MaxOrderItemsExceededException extends Exception {


    private int maxAllowedItems;

    public MaxOrderItemsExceededException(int maxAllowedItems) {
        this.maxAllowedItems = maxAllowedItems;
    }

    public int getMaxAllowedItems() {
        return maxAllowedItems;
    }
}
