package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.User;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    private final Long NON_EXISTENT_ID = -1L;

    @Autowired
    private UserService userService;


    private User createUser(String userName, User.RoleType role, User.StatusType status) {
        return new User(userName, "password", "FirstName", "LastName",
                userName + "@example.com", "123456789", null, role, status);
    }

    @Test
    public void testSignUpAndLoginFromId() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUser("user", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        User loggedInUser = userService.loginFromId(user.getId());
        assertEquals(user, loggedInUser);
        assertEquals(User.RoleType.CLIENT, loggedInUser.getRole());
        assertEquals(User.StatusType.ACTIVE, loggedInUser.getStatus());
    }

    @Test
    public void testSignUpDuplicatedUserName() throws DuplicateInstanceException {
        User user = createUser("user", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        assertThrows(DuplicateInstanceException.class, () -> userService.signUp(user));
    }

    @Test
    public void testLoginFromNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> userService.loginFromId(NON_EXISTENT_ID));
    }

    @Test
    public void testLogin() throws DuplicateInstanceException, IncorrectLoginException, UserBannedException {
        User user = createUser("user", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        String noEncodePassword = user.getPassword();
        userService.signUp(user);

        User loggedInUser = userService.login(user.getUserName(), noEncodePassword);
        assertEquals(user, loggedInUser);
    }

    @Test
    public void testLoginWithIncorrectPassword() throws DuplicateInstanceException {
        User user = createUser("user", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        assertThrows(IncorrectLoginException.class, () ->
                userService.login(user.getUserName(), "wrongPassword"));
    }

    @Test
    public void testLoginWithNonExistentUserName() {
        assertThrows(IncorrectLoginException.class, () -> userService.login("nonexistent", "password"));
    }

    @Test
    public void testLoginBannedUser() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUser("bannedUser", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        String noEncodePassword = user.getPassword();

        userService.signUp(user);

        userService.banUser(user.getId());

       assertThrows(UserBannedException.class, () ->
                userService.login(user.getUserName(), noEncodePassword));
    }


    @Test
    public void testUpdateProfile() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        user.setFirstName("UpdatedFirstName");
        user.setLastName("UpdatedLastName");
        user.setEmail("updated@example.com");

        userService.updateProfile(user.getId(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getPhoneNumber(), null);

        User updatedUser = userService.loginFromId(user.getId());
        assertEquals("UpdatedFirstName", updatedUser.getFirstName());
        assertEquals("UpdatedLastName", updatedUser.getLastName());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    public void testUpdateProfileWithNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () ->
                userService.updateProfile(NON_EXISTENT_ID, "FirstName", "LastName", "email@example.com", "123456789", null));
    }

    @Test
    public void testChangePassword() throws DuplicateInstanceException, InstanceNotFoundException, IncorrectPasswordException, IncorrectLoginException {
        User user = createUser("user", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        String oldPassword = user.getPassword();
        String newPassword = "newPassword";
        userService.signUp(user);

        userService.changePassword(user.getId(), oldPassword, newPassword);

        assertDoesNotThrow(() -> userService.login(user.getUserName(), newPassword));
    }

    @Test
    public void testChangePasswordWithNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> userService.changePassword(NON_EXISTENT_ID, "oldPassword", "newPassword"));
    }

    @Test
    public void testChangePasswordWithIncorrectPassword() throws DuplicateInstanceException {
        User user = createUser("user", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        assertThrows(IncorrectPasswordException.class, () ->
                userService.changePassword(user.getId(), "wrongPassword", "newPassword"));
    }

    @Test
    public void testBanAndUnbanUser() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUser("user", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(user);

        userService.banUser(user.getId());
        assertEquals(User.StatusType.BANNED, userService.loginFromId(user.getId()).getStatus());

        userService.unbanUser(user.getId());
        assertEquals(User.StatusType.ACTIVE, userService.loginFromId(user.getId()).getStatus());
    }

    @Test
    public void testBanUserWithNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> userService.banUser(NON_EXISTENT_ID));
    }

    @Test
    public void testUnbanUserWithNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> userService.unbanUser(NON_EXISTENT_ID));
    }

    @Test
    public void testFindAllUsers() throws DuplicateInstanceException {
        User user1 = createUser("user1", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        User user2 = createUser("user2", User.RoleType.ADMIN, User.StatusType.BANNED);
        userService.signUp(user1);
        userService.signUp(user2);

        List<User> users = userService.findAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
}
