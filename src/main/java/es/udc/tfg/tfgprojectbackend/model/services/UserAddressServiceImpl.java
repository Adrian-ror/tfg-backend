package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.User;
import es.udc.tfg.tfgprojectbackend.model.entities.UserAddress;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.entities.UserAddressDao;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of UserAddressService interface for managing user addresses.
 */
@Service
@Transactional
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressDao userAddressDao;

    @Autowired
    private PermissionChecker permissionChecker;

    /**
     * Adds a new user address.
     *
     * @param userId       the ID of the user to whom the address belongs
     * @param addressLine1 the first line of the address
     * @param addressLine2 the second line of the address (optional)
     * @param city         the city of the address
     * @param state        the state of the address
     * @param postalCode   the postal code of the address
     * @param country      the country of the address
     * @param phoneNumber  the phone number associated with the address (optional)
     * @param isDefault    whether this address should be set as the default for the user
     * @return a list of UserAddress entities after adding the new address
     * @throws InstanceNotFoundException if the user does not exist
     * @throws DuplicateInstanceException if the address already exists for the user
     */
    @Override
    public UserAddress addUserAddress(Long userId, String addressLine1, String addressLine2, String city,
                                            String state, String postalCode, String country, String phoneNumber,
                                            boolean isDefault) throws InstanceNotFoundException, DuplicateInstanceException {
        User user = permissionChecker.checkUser(userId);
        checkIfAddressExists(userId, addressLine1, addressLine2, city, state, postalCode, country, phoneNumber);

        UserAddress userAddress = new UserAddress(user, addressLine1, addressLine2, city, state, postalCode,
                country, phoneNumber, isDefault);

        if (isDefault) {
            userAddressDao.findUserAddressByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(address -> address.setIsDefault(false));
        }

        userAddressDao.save(userAddress);

        return userAddress;
    }

    /**
     * Updates an existing user address.
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
     * @param isDefault    whether this address should be set as the default for the user
     * @return a list of UserAddress entities after updating the address
     * @throws InstanceNotFoundException if the user or address does not exist
     */
    @Override
    public UserAddress updateUserAddress(Long userId, Long addressId, String addressLine1, String addressLine2,
                                               String city, String state, String postalCode, String country,
                                               String phoneNumber, boolean isDefault) throws InstanceNotFoundException, DuplicateInstanceException {
        permissionChecker.checkUser(userId);
        UserAddress userAddress = getUserAddressById(userId, addressId);

        // Update user address details
        userAddress.setAddressLine1(addressLine1);
        userAddress.setAddressLine2(addressLine2);
        userAddress.setCity(city);
        userAddress.setState(state);
        userAddress.setPostalCode(postalCode);
        userAddress.setCountry(country);
        userAddress.setPhoneNumber(phoneNumber);

        if (isDefault && !userAddress.getIsDefault()) {
            userAddressDao.findUserAddressByUserIdAndIsDefaultTrue(userId)
                    .ifPresent(address -> address.setIsDefault(false));
        }

        userAddress.setIsDefault(isDefault);



        userAddressDao.save(userAddress);
        return userAddress;
    }

    /**
     * Removes a user address.
     *
     * @param userId    the ID of the user to whom the address belongs
     * @param addressId the ID of the address to remove
     * @return a list of UserAddress entities after removing the address
     * @throws InstanceNotFoundException if the user or address does not exist
     * @throws PermissionException if the user does not have permission to remove the address
     */
    @Override
    public List<UserAddress> removeUserAddress(Long userId, Long addressId) throws InstanceNotFoundException, PermissionException {
        permissionChecker.checkUser(userId);
        UserAddress userAddress = getUserAddressById(userId, addressId);
        checkUserPermission(userId, userAddress);

        userAddressDao.delete(userAddress);
        return userAddressDao.findUserAddressesByUserId(userId);
    }

    /**
     * Retrieves the default user address for a specified user.
     *
     * @param userId the ID of the user
     * @return the default UserAddress entity, or null if none exists
     */
    @Override
    public UserAddress findDefaultUserAddress(Long userId) {
        return userAddressDao.findUserAddressByUserIdAndIsDefaultTrue(userId).orElse(null);
    }

    /**
     * Retrieves a user address by its ID.
     *
     * @param addressId the ID of the address to retrieve
     * @return the UserAddress entity
     * @throws InstanceNotFoundException if the address does not exist
     */
    @Override
    public UserAddress findUserAddressById(Long userId, Long addressId) throws InstanceNotFoundException {
        return getUserAddressById(userId, addressId);
    }

    /**
     * Retrieves all user addresses associated with a specified user.
     *
     * @param userId the ID of the user
     * @return a list of UserAddress entities
     * @throws InstanceNotFoundException if the user does not exist
     */
    @Override
    public List<UserAddress> findUserAddresses(Long userId) throws InstanceNotFoundException {
        permissionChecker.checkUser(userId);
        return userAddressDao.findUserAddressesByUserId(userId);
    }

    /**
     * Retrieves a user address by its ID and throws an exception if not found.
     *
     * @param addressId the ID of the address
     * @return the UserAddress entity
     * @throws InstanceNotFoundException if the address does not exist
     */
    private UserAddress getUserAddressById(Long userId, Long addressId) throws InstanceNotFoundException {
        return userAddressDao.findByUserIdAndId(userId, addressId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.userAddress", addressId));
    }

    /**
     * Checks if an address already exists for a user.
     *
     * @param userId       the ID of the user
     * @param addressLine1 the first line of the address
     * @param addressLine2 the second line of the address (optional)
     * @param city         the city of the address
     * @param state        the state of the address
     * @param postalCode   the postal code of the address
     * @param country      the country of the address
     * @param phoneNumber  the phone number associated with the address (optional)
     * @throws DuplicateInstanceException if the address already exists
     */
    private void checkIfAddressExists(Long userId, String addressLine1, String addressLine2, String city, String state,
                                      String postalCode, String country, String phoneNumber) throws DuplicateInstanceException {
        List<UserAddress> existingAddresses = userAddressDao.findUserAddressesByUserId(userId);
        for (UserAddress existingAddress : existingAddresses) {
            if (isAddressEqual(existingAddress, addressLine1, addressLine2, city, state, postalCode, country, phoneNumber)) {
                throw new DuplicateInstanceException("project.entities.userAddress", addressLine1);
            }
        }
    }

    /**
     * Compares the specified address with another address.
     *
     * @param address       the UserAddress entity to compare
     * @param addressLine1  the first line of the address
     * @param addressLine2  the second line of the address (optional)
     * @param city          the city of the address
     * @param state         the state of the address
     * @param postalCode    the postal code of the address
     * @param country       the country of the address
     * @param phoneNumber   the phone number associated with the address (optional)
     * @return true if the addresses are equal, false otherwise
     */
    private boolean isAddressEqual(UserAddress address, String addressLine1, String addressLine2, String city,
                                   String state, String postalCode, String country, String phoneNumber) {
        return address.getAddressLine1().equals(addressLine1) &&
                address.getAddressLine2().equals(addressLine2) &&
                address.getCity().equals(city) &&
                address.getState().equals(state) &&
                address.getPostalCode().equals(postalCode) &&
                address.getCountry().equals(country) &&
                address.getPhoneNumber().equals(phoneNumber);
    }

    /**
     * Checks if the user has permission to remove the specified address.
     *
     * @param userId      the ID of the user
     * @param userAddress the UserAddress entity to check
     * @throws PermissionException if the user does not have permission
     */
    private void checkUserPermission(Long userId, UserAddress userAddress) throws PermissionException {
        if (!userAddress.getUser().getId().equals(userId)) {
            throw new PermissionException();
        }
    }
}
