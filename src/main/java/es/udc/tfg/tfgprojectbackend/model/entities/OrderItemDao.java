package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Data Access Object interface for managing OrderItem entities.
 * Extends CrudRepository and PagingAndSortingRepository to provide basic CRUD operations
 * and pagination capabilities for OrderItem entities.
 */
public interface OrderItemDao extends CrudRepository<OrderItem, Long>, PagingAndSortingRepository<OrderItem, Long> {

    /**
     * Finds all order items for products uploaded by a specific user.
     *
     * @param userId the ID of the user whose uploaded products' order items are to be retrieved
     * @return a list of order items for products uploaded by the specified user
     */
    List<OrderItem> findOrderItemByProductUserId(Long userId);

    /**
     * Checks if any order items exist for a specific product.
     *
     * @param productId the ID of the product to check for existing order items
     * @return true if order items exist for the specified product; false otherwise
     */
    boolean existsOrderItemByProductId(Long productId);
}
