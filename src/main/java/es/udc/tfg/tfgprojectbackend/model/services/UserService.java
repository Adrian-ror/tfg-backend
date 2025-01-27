package es.udc.tfg.tfgprojectbackend.model.services;
import es.udc.tfg.tfgprojectbackend.model.entities.User;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import java.util.List;

/**
 * Service interface for managing users in the system.
 */
public interface UserService {

    /**
     * Registers a new user.
     *
     * @param user the user to register.
     * @throws DuplicateInstanceException if the user already exists.
     */
    void signUp(User user) throws DuplicateInstanceException;

    /**
     * Logs in a user using their username and password.
     *
     * @param userName the user's username.
     * @param password the user's password.
     * @return the user who has logged in.
     * @throws IncorrectLoginException if the login credentials are incorrect.
     * @throws UserBannedException if the user is banned.
     */
    User login(String userName, String password)
            throws IncorrectLoginException, UserBannedException;

    /**
     * Logs in a user by their user ID.
     *
     * @param id the user's ID.
     * @return the user with the given ID.
     * @throws InstanceNotFoundException if the user does not exist.
     */
    User loginFromId(Long id) throws InstanceNotFoundException;

    /**
     * Updates the profile information of a user.
     *
     * @param id the user's ID.
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @param email the user's email address.
     * @param phoneNumber the user's phone number (optional).
     * @param image the user's profile image (optional).
     * @return the updated user.
     * @throws InstanceNotFoundException if the user does not exist.
     */
    User updateProfile(Long id, String firstName, String lastName, String email, String phoneNumber, String image)
            throws InstanceNotFoundException;

    /**
     * Changes the user's password.
     *
     * @param id the user's ID.
     * @param oldPassword the user's current password.
     * @param newPassword the user's new password.
     * @throws InstanceNotFoundException if the user does not exist.
     * @throws IncorrectPasswordException if the current password is incorrect.
     */
    void changePassword(Long id, String oldPassword, String newPassword)
            throws InstanceNotFoundException, IncorrectPasswordException;

    /**
     * Retrieves all users in the system.
     *
     * @return a list of all users.
     */
    List<User> findAllUsers();

    /**
     * Retrieves all active users in the system.
     *
     * @return a list of active users.
     */
    List<User> findAllActiveUsers();

    /**
     * Retrieves all banned users in the system.
     *
     * @return a list of banned users.
     */
    List<User> findAllBannedUsers();

    /**
     * Bans a user's account, preventing them from accessing the system.
     *
     * @param id the user's ID.
     * @throws InstanceNotFoundException if the user does not exist.
     */
    void banUser(Long id) throws InstanceNotFoundException;

    /**
     * Unbans a user's account, restoring their access to the system.
     *
     * @param id the user's ID.
     * @throws InstanceNotFoundException if the user does not exist.
     */
    void unbanUser(Long id) throws InstanceNotFoundException;
}
