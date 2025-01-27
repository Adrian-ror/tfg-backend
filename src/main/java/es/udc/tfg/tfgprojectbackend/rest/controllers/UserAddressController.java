package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.UserAddress;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;
import es.udc.tfg.tfgprojectbackend.model.services.UserAddressService;
import es.udc.tfg.tfgprojectbackend.rest.dtos.UserAddressDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.UserAddressParamsDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.UserAddressConversor.*;

@RestController
@RequestMapping("/user-addresses")
public class UserAddressController {


    @Autowired
    private UserAddressService userAddressService;


    @PostMapping
    public UserAddressDto addUserAddress(@RequestAttribute Long userId,
                                               @Validated @RequestBody UserAddressParamsDto params)
            throws InstanceNotFoundException, DuplicateInstanceException {
        UserAddress address = userAddressService.addUserAddress(userId, params.getAddressLine1(),
                params.getAddressLine2(), params.getCity(), params.getState(), params.getPostalCode(),
                params.getCountry(), params.getPhoneNumber(), params.getIsDefault());
        return toUserAddressDto(address);
    }


    @PutMapping("/{addressId}")
    public UserAddressDto updateUserAddress(@RequestAttribute Long userId, @PathVariable Long addressId,
                                                  @Validated @RequestBody UserAddressParamsDto params)
            throws InstanceNotFoundException, DuplicateInstanceException {
        UserAddress address = userAddressService.updateUserAddress(userId, addressId, params.getAddressLine1(),
                params.getAddressLine2(), params.getCity(), params.getState(), params.getPostalCode(),
                params.getCountry(), params.getPhoneNumber(), params.getIsDefault());
        return toUserAddressDto(address);
    }


    @DeleteMapping("/{addressId}")
    public List<UserAddressDto> removeUserAddress(@RequestAttribute Long userId, @PathVariable Long addressId)
            throws InstanceNotFoundException, PermissionException {
        List<UserAddress> addresses = userAddressService.removeUserAddress(userId, addressId);
        return toUserAddressDtos(addresses);
    }


    @GetMapping("/default")
    public UserAddressDto findDefaultUserAddress(@RequestAttribute Long userId) {

        UserAddress address = userAddressService.findDefaultUserAddress(userId);
        return address != null ? toUserAddressDto(address) : null;
    }

    @GetMapping("/{addressId}")
    public UserAddressDto findUserAddressById(@RequestAttribute Long userId, @PathVariable Long addressId)
            throws InstanceNotFoundException {
        UserAddress address = userAddressService.findUserAddressById(userId, addressId);
        return toUserAddressDto(address);
    }

    @GetMapping
    public List<UserAddressDto> findUserAddresses(@RequestAttribute Long userId)
            throws InstanceNotFoundException {
        List<UserAddress> addresses = userAddressService.findUserAddresses(userId);
        return toUserAddressDtos(addresses);
    }
}
