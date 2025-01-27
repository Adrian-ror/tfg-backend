package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.util.List;

/**
 * Data transfer object for blocks of items.
 *
 * @param <T> the type of the items.
 */
public class BlockDto<T> {

    private List<T> items;
    private int totalItems;
    private boolean existMoreItems;

    public BlockDto() {}

    public BlockDto(List<T> items, int totalItems, boolean existMoreItems) {
        this.items = items;
        this.totalItems = totalItems;
        this.existMoreItems = existMoreItems;
    }

    /**
     * Gets the list of items in the block.
     *
     * @return the list of items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * Sets the list of items in the block.
     *
     * @param items the new list of items
     */
    public void setItems(List<T> items) {
        this.items = items;
    }

    /**
     * Gets the total number of items.
     *
     * @return the total number of items
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * Sets the total number of items.
     *
     * @param totalItems the total number of items
     */
    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    /**
     * Checks if there are more items available.
     *
     * @return true if there are more items; false otherwise
     */
    public boolean existsMoreItems() {
        return existMoreItems;
    }

    /**
     * Sets whether there are more items available.
     *
     * @param existMoreItems true if there are more items; false otherwise
     */
    public void setExistMoreItems(boolean existMoreItems) {
        this.existMoreItems = existMoreItems;
    }
}
