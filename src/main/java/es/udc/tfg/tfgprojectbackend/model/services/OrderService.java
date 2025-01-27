package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.Order;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;

/**
 * Service interface for managing orders in the system.
 */
public interface OrderService {

    /**
     * Finds an order by its ID for a specific user.
     *
     * @param userId the ID of the user
     * @param orderId the ID of the order
     * @return the found order
     * @throws InstanceNotFoundException if the order or user does not exist
     * @throws PermissionException if the user does not have permission to view the order
     */
    Order findOrder(Long userId, Long orderId) throws InstanceNotFoundException, PermissionException;

    /**
     * Retrieves a block of orders for a specific user.
     *
     * @param userId the ID of the user
     * @param page the page number for pagination
     * @param size the number of orders per page
     * @return a block of orders
     */
    Block<Order> findOrders(Long userId, int page, int size);

    /**
     * Retrieves a block of orders for a specific user filtered by order state.
     *
     * @param userId the ID of the user
     * @param state the state of the orders to filter by
     * @param page the page number for pagination
     * @param size the number of orders per page
     * @return a block of orders matching the state filter
     */
    Block<Order> findOrdersByState(Long userId, Order.OrderState state, int page, int size);

    /**
     * Changes the state of an existing order.
     *
     * @param orderId the ID of the order to update
     * @param newState the new state for the order
     * @throws InstanceNotFoundException if the order does not exist
     */
    void changeOrderState(Long orderId, Order.OrderState newState) throws InstanceNotFoundException;

    /**
     * Retrieves a block of all orders in the system.
     *
     * @param page the page number for pagination
     * @param size the number of orders per page
     * @return a block of all orders
     */
    Block<Order> findAllOrders(int page, int size);
}
