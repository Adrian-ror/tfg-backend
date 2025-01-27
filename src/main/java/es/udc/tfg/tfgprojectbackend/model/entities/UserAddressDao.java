package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing UserAddress entities.
 * Extends CrudRepository and PagingAndSortingRepository for CRUD and pagination operations.
 */
public interface UserAddressDao extends CrudRepository<UserAddress, Long>, PagingAndSortingRepository<UserAddress, Long> {

    /**
     * Finds a user address by the user ID and address ID.
     *
     * @param userId the ID of the user.
     * @param id the ID of the user address.
     * @return an Optional containing the user address if found, or empty if not.
     */
    Optional<UserAddress> findByUserIdAndId(Long userId, Long id);

    /**
     * Finds all addresses associated with a specific user.
     *
     * @param userId the ID of the user.
     * @return a list of user addresses for the given user.
     */
    List<UserAddress> findUserAddressesByUserId(Long userId);

    /**
     * Finds the default address for a specific user.
     *
     * @param userId the ID of the user.
     * @return an Optional containing the default user address if it exists, or empty if not.
     */
    Optional<UserAddress> findUserAddressByUserIdAndIsDefaultTrue(Long userId);

}
