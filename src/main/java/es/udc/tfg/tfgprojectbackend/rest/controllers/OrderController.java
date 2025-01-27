package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.Order;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;
import es.udc.tfg.tfgprojectbackend.model.services.Block;
import es.udc.tfg.tfgprojectbackend.model.services.OrderService;
import es.udc.tfg.tfgprojectbackend.rest.dtos.BlockDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.OrderConversor.toOrderDto;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.OrderConversor.toOrderDtos;

/**
 * Controller to manage order operations.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;

    /**
     * Retrieves an order by its ID.
     *
     * @param userId  the ID of the user requesting the order.
     * @param orderId the ID of the order to retrieve.
     * @return OrderDto object with the order details.
     * @throws InstanceNotFoundException if the order does not exist.
     * @throws PermissionException       if the user does not have permission.
     */
    @GetMapping("/{orderId}")
    public OrderDto findOrder(@RequestAttribute Long userId, @PathVariable Long orderId)
            throws InstanceNotFoundException, PermissionException {

        return toOrderDto(orderService.findOrder(userId, orderId));
    }

    /**
     * Retrieves all orders for a user with pagination.
     *
     * @param userId the ID of the user.
     * @param page   the page number for pagination.
     * @return BlockDto containing a list of OrderDto objects.
     */
    @GetMapping
    public BlockDto<OrderDto> findOrders(@RequestAttribute Long userId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue= "6") int size) {

        Block<Order> orderBlock = orderService.findOrders(userId, page, size);
        return new BlockDto<>(toOrderDtos(orderBlock.getItems()), orderBlock.getTotalItems(), orderBlock.existsMoreItems());
    }


    /**
     * Retrieves all orders for a user by their state with pagination.
     *
     * @param userId the ID of the user.
     * @param state  the state of the orders to retrieve.
     * @param page   the page number for pagination.
     * @return BlockDto containing a list of OrderDto objects.
     */
    @GetMapping("/state")
    public BlockDto<OrderDto> findOrdersByState(@RequestAttribute Long userId,
                                                @RequestParam Order.OrderState state,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue= "6") int size) {
        Block<Order> orderBlock = orderService.findOrdersByState(userId, state, page, size);
        return new BlockDto<>(toOrderDtos(orderBlock.getItems()), orderBlock.getTotalItems(), orderBlock.existsMoreItems());
    }

    /**
     * Changes the state of an order.
     *
     * @param orderId  the ID of the order to change.
     * @param newState the new state for the order.
     * @throws InstanceNotFoundException if the order does not exist.
     */
    @PutMapping("/{orderId}/state")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeOrderState(@PathVariable Long orderId, @RequestParam String newState) throws InstanceNotFoundException {

        orderService.changeOrderState(orderId, Order.OrderState.valueOf(newState));
    }


    /**
     * Retrieves all orders with pagination.
     *
     * @param page the page number for pagination.
     * @param size the number of items per page.
     * @return BlockDto containing a list of OrderDto objects.
     */
    @GetMapping("/all")
    public BlockDto<OrderDto> findAllOrders(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue= "6") int size) {

        Block<Order> orderBlock = orderService.findAllOrders(page, size);
        return new BlockDto<>(toOrderDtos(orderBlock.getItems()), orderBlock.getTotalItems(), orderBlock.existsMoreItems());
    }



}
