package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.User;
import es.udc.tfg.tfgprojectbackend.model.entities.UserAddress;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.UserAddressService;
import es.udc.tfg.tfgprojectbackend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserAddressServiceTest {

    private final Long NON_EXISTENT_ID = -1L;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private UserService userService;

    private User createUser(String userName, User.RoleType role, User.StatusType status) {
        return new User(userName, "password", "FirstName", "LastName",
                userName + "@example.com", "123456789", null, role, status);
    }

    private UserAddress createUserAddress(User user, boolean isDefault) {
        return new UserAddress(user, "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", isDefault);
    }

    @Test
    public void testAddUserAddress() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        UserAddress address = userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true);

        assertNotNull(address);
        assertEquals(user.getId(), address.getUser().getId());
        assertTrue(address.getIsDefault());
    }

    @Test
    public void testAddDuplicateUserAddress() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true);

        assertThrows(DuplicateInstanceException.class, () ->
                userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", false));
    }

    @Test
    public void testUpdateUserAddress() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        UserAddress address = userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true);

        userAddressService.updateUserAddress(user.getId(), address.getId(), "456 Oak St", "Apt 5", "NewCity", "NewState", "54321", "NewCountry", "987654322", true);

        UserAddress updatedAddress = userAddressService.findUserAddressById(user.getId(), address.getId());
        assertEquals("456 Oak St", updatedAddress.getAddressLine1());
        assertEquals("NewCity", updatedAddress.getCity());
    }

    @Test
    public void testRemoveUserAddress() throws InstanceNotFoundException, PermissionException, DuplicateInstanceException {
        User user = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        UserAddress address = userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true);

        List<UserAddress> addressesBefore = userAddressService.findUserAddresses(user.getId());
        assertEquals(1, addressesBefore.size());

        userAddressService.removeUserAddress(user.getId(), address.getId());

        List<UserAddress> addressesAfter = userAddressService.findUserAddresses(user.getId());
        assertEquals(0, addressesAfter.size());
    }

    @Test
    public void testFindUserAddressById() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        UserAddress address = userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true);

        UserAddress retrievedAddress = userAddressService.findUserAddressById(user.getId(), address.getId());
        assertEquals(address, retrievedAddress);
    }

    @Test
    public void testFindDefaultUserAddress() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true);
        userAddressService.addUserAddress(user.getId(), "456 Oak St", "Apt 5", "NewCity", "NewState", "54321", "NewCountry",null, false);

        UserAddress defaultAddress = userAddressService.findDefaultUserAddress(user.getId());
        assertNotNull(defaultAddress);
        assertTrue(defaultAddress.getIsDefault());
    }

    @Test
    public void testFindUserAddresses() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true);
        userAddressService.addUserAddress(user.getId(), "456 Oak St", "Apt 5", "NewCity", "NewState", "54321", "NewCountry",null, false);

        List<UserAddress> addresses = userAddressService.findUserAddresses(user.getId());
        assertEquals(2, addresses.size());
    }

    @Test
    public void testRemoveUserAddressWithoutPermission() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        User anotherUser = createUser("user2", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(anotherUser);

        UserAddress address = userAddressService.addUserAddress(user.getId(), "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true);

        assertThrows(InstanceNotFoundException.class, () ->
                userAddressService.removeUserAddress(anotherUser.getId(), address.getId()));
    }

    @Test
    public void testAddAddressForNonExistentUser() {
        assertThrows(InstanceNotFoundException.class, () ->
                userAddressService.addUserAddress(NON_EXISTENT_ID, "123 Main St", "Apt 4", "City", "State", "12345", "Country", "987654321", true));
    }
}
