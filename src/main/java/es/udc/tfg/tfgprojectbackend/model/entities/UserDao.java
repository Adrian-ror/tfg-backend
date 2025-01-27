package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for User entity. It extends CrudRepository to have access to basic CRUD operations
 * and PagingAndSortingRepository to have access to pagination and sorting operations.
 */
public interface UserDao extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    /**
     * Method to check if a user with the given username exists in the database.
     *
     * @param userName the username to check
     * @return true if the user exists, false otherwise
     */
    boolean existsByUserName(String userName);

    /**
     * Method to find a user by their username.
     *
     * @param userName the username to search for
     * @return an Optional containing the user with the given username, or an empty Optional if not found
     */
    Optional<User> findByUserName(String userName);

    /**
     * Method to find users by their status.
     *
     * @param status the status of the users to find
     * @return a list of users with the specified status
     */
    List<User> findByStatus(User.StatusType status);
}
