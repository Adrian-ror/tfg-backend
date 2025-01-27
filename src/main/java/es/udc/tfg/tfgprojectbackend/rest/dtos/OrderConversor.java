package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.Order;
import es.udc.tfg.tfgprojectbackend.model.entities.OrderItem;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConversor {

    private OrderConversor() {}

    public static List<OrderSummaryDto> toOrderSummaryDtos(List<Order> orders) {
        return orders.stream()
                .map(OrderConversor::toOrderSummaryDto)
                .collect(Collectors.toList());
    }

    public static OrderDto toOrderDto(Order order) {
        List<OrderItemDto> items = order.getItems().stream()
                .map(OrderConversor::toOrderItemDto)
                .sorted(Comparator.comparing(OrderItemDto::getProductName))
                .collect(Collectors.toList());

        return new OrderDto(
                order.getId(),
                items,
                toMillis(order.getDate()),
                order.getTotalPrice(),
                order.getUserAddress().getPostalCode(),
                order.getUserAddress().getState(),
                order.getState().toString(),
                order.getPaymentMethod().getId(),
                order.getPaymentMethod().getBrand(),
                order.getPaymentMethod().getLast4(),
                order.getPaymentMethod().getFunding(),
                order.getShippingMethod().getName(),
                order.getShippingMethod().getEstimatedDeliveryTime()
        );
    }

    public static List<OrderDto> toOrderDtos(List<Order> orders) {
        return orders.stream()
                .map(OrderConversor::toOrderDto)
                .collect(Collectors.toList());
    }

    private static OrderSummaryDto toOrderSummaryDto(Order order) {
        return new OrderSummaryDto(order.getId(), toMillis(order.getDate()));
    }

    private static OrderItemDto toOrderItemDto(OrderItem item) {
        return new OrderItemDto(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getCategory().getName(),
                item.getProductPrice(),
                item.getQuantity()
        );
    }

    private static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES)
                .atZone(ZoneOffset.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
