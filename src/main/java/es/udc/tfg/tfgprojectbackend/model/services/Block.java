package es.udc.tfg.tfgprojectbackend.model.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A generic class that represents a block of paginated items.
 *
 * @param <T> the type of items in the block (could be products, orders, etc.)
 */
public class Block<T> {

    private final List<T> items;
    private final int totalItems;
    private final boolean existMoreItems;

    /**
     * Constructs a block of items.
     *
     * @param items        the list of items in the block
     * @param totalItems   the total number of items
     * @param existMoreItems indicates whether there are more items available
     */
    public Block(List<T> items, int totalItems, boolean existMoreItems) {
        this.items = Optional.ofNullable(items).orElse(Collections.emptyList());
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
     * Gets the total number of items.
     *
     * @return the total number of items
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     * Checks if there are more items available.
     *
     * @return true if there are more items; false otherwise
     */
    public boolean existsMoreItems() {
        return existMoreItems;
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, totalItems, existMoreItems);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Block<?> other = (Block<?>) obj;
        return totalItems == other.totalItems &&
                existMoreItems == other.existMoreItems &&
                items.equals(other.items);
    }
}
