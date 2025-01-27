package es.udc.tfg.tfgprojectbackend.rest.dtos;
import es.udc.tfg.tfgprojectbackend.model.entities.ShippingMethod;

import java.util.List;
import java.util.stream.Collectors;

public class ShippingMethodConversor {

    public static ShippingMethodDto toShippingMethodDto(ShippingMethod shippingMethod) {
        return new ShippingMethodDto(
                shippingMethod.getId(),
                shippingMethod.getName(),
                shippingMethod.getDescription(),
                shippingMethod.getShippingCost(),
                shippingMethod.getEstimatedDeliveryTime()
        );
    }

    public static List<ShippingMethodDto> toShippingMethodDtos(List<ShippingMethod> shippingMethods) {
        return shippingMethods.stream()
                .map(ShippingMethodConversor::toShippingMethodDto)
                .collect(Collectors.toList());
    }

    public static ShippingMethod toShippingMethod(ShippingMethodDto shippingMethodDto) {
        return new ShippingMethod(
                shippingMethodDto.getName(),
                shippingMethodDto.getDescription(),
                shippingMethodDto.getShippingCost(),
                shippingMethodDto.getEstimatedDeliveryTime()
        );
    }
}
