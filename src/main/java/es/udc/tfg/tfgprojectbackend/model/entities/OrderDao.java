package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface OrderDao extends CrudRepository<Order, Long>, PagingAndSortingRepository<Order, Long> {

    /**
     * Finds orders placed by a user, ordered by date in descending order.
     *
     * @param userId the ID of the user whose orders are to be retrieved
     * @param pageable pagination information
     * @return a slice of orders for the user
     */
    Slice<Order> findByUserIdOrderByDateDesc(Long userId, Pageable pageable);

    /**
     * Finds all orders, ordered by date in descending order.
     *
     * @param pageable pagination information
     * @return a slice of orders
     */
    Slice<Order> findAllByOrderByDateDesc(Pageable pageable);

    /**
     * Counts the total number of orders placed by a user.
     *
     * @param userId the ID of the user whose orders are to be counted
     * @return the total number of orders for the user
     */
    long countByUserId(Long userId);

    /**
     * Finds orders with a specific state, ordered by date in descending order.
     *
     * @param state the state of the orders to be retrieved
     * @param pageable pagination information
     * @return a slice of orders with the specified state
     */
    Slice<Order> findByStateOrderByDateDesc(Order.OrderState state, Pageable pageable);

    /**
     * Counts the total number of orders placed by a user with a specific state.
     *
     * @param userId the ID of the user whose orders are to be counted
     * @param state the state of the orders to be counted
     * @return the total number of orders for the user with the specified state
     */
    long countByUserIdAndState(Long userId, Order.OrderState state);

    /**
     * Checks if a user has placed an order for a specific product.
     *
     * @param userId the ID of the user to check
     * @param productId the ID of the product to check
     * @return true if the user has placed an order containing the product, false otherwise
     */
    @Query("SELECT COUNT(o) > 0 FROM Order o JOIN o.items i WHERE o.user.id = :userId AND i.product.id = :productId")
    boolean existsByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);
}
