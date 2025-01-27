package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.UserAddress;

import java.util.List;
import java.util.stream.Collectors;

public class UserAddressConversor {

    public static UserAddressDto toUserAddressDto(UserAddress address) {
        return new UserAddressDto(address.getId(), address.getAddressLine1(), address.getAddressLine2(), address.getCity(),
                address.getState(), address.getPostalCode(), address.getCountry(), address.getPhoneNumber(),
                address.getIsDefault());
    }

    public static List<UserAddressDto> toUserAddressDtos(List<UserAddress> addresses) {
        return addresses.stream()
                .map(UserAddressConversor::toUserAddressDto)
                .collect(Collectors.toList());
    }
}
