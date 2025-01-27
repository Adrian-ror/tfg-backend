package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.UserAddress;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;

import java.util.List;

/**
 * Service interface for managing user addresses.
 */
public interface UserAddressService {

    /**
     * Adds a new user address and returns the updated list of addresses.
     *
     * @param userId       the ID of the user to whom the address belongs
     * @param addressLine1 the first line of the address
     * @param addressLine2 the second line of the address (optional)
     * @param city         the city of the address
     * @param state        the state of the address
     * @param postalCode   the postal code of the address
     * @param country      the country of the address
     * @param phoneNumber  the phone number associated with the address (optional)
     * @param isDefault    whether to set this address as the default for the user
     * @return the updated UserAddress entity
     * @throws InstanceNotFoundException if the user does not exist
     * @throws DuplicateInstanceException if the address already exists for the user
     */
    UserAddress addUserAddress(Long userId, String addressLine1, String addressLine2, String city,
                               String state, String postalCode, String country,
                               String phoneNumber, boolean isDefault)
            throws InstanceNotFoundException, DuplicateInstanceException;

    /**
     * Updates an existing user address and returns the updated address.
     *
     * @param userId       the ID of the user to whom the address belongs
     * @param addressId    the ID of the address to update
     * @param addressLine1 the new first line of the address
     * @param addressLine2 the new second line of the address (optional)
     * @param city         the new city of the address
     * @param state        the new state of the address
     * @param postalCode   the new postal code of the address
     * @param country      the new country of the address
     * @param phoneNumber  the new phone number associated with the address (optional)
     * @param isDefault    whether to set this address as the default for the user
     * @return the updated UserAddress entity
     * @throws InstanceNotFoundException if the user or address does not exist
     * @throws DuplicateInstanceException if the address already exists for the user
     */
    UserAddress updateUserAddress(Long userId, Long addressId, String addressLine1, String addressLine2,
                                  String city, String state, String postalCode, String country,
                                  String phoneNumber, boolean isDefault)
            throws InstanceNotFoundException, DuplicateInstanceException;

    /**
     * Removes a user address and returns the updated list of addresses.
     *
     * @param userId    the ID of the user to whom the address belongs
     * @param addressId the ID of the address to remove
     * @return the updated list of UserAddress entities
     * @throws InstanceNotFoundException if the user or address does not exist
     * @throws PermissionException if the user does not have permission to remove the address
     */
    List<UserAddress> removeUserAddress(Long userId, Long addressId) throws InstanceNotFoundException, PermissionException;

    /**
     * Retrieves the default address for a user.
     *
     * @param userId the ID of the user
     * @return the default UserAddress entity, or null if none exists
     */
    UserAddress findDefaultUserAddress(Long userId);

    /**
     * Retrieves a user address by its ID.
     *
     * @param addressId the ID of the address to retrieve
     * @return the UserAddress entity
     * @throws InstanceNotFoundException if the address does not exist
     */
    UserAddress findUserAddressById(Long userId, Long addressId) throws InstanceNotFoundException;

    /**
     * Lists all addresses associated with a user.
     *
     * @param userId the ID of the user
     * @return a list of UserAddress entities
     * @throws InstanceNotFoundException if the user does not exist
     */
    List<UserAddress> findUserAddresses(Long userId) throws InstanceNotFoundException;
}
