package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing PaymentMethod entities.
 * Extends CrudRepository and PagingAndSortingRepository for CRUD and pagination operations.
 */
public interface PaymentMethodDao extends CrudRepository<PaymentMethod, Long>, PagingAndSortingRepository<PaymentMethod, Long> {

    /**
     * Finds all payment methods associated with a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of payment methods for the given user.
     */
    List<PaymentMethod> findByUserId(Long userId);

    /**
     * Finds the default payment method for a specific user.
     *
     * @param userId The ID of the user.
     * @return An Optional containing the default payment method if it exists.
     */
    Optional<PaymentMethod> findByUserIdAndByDefaultTrue(Long userId);

    /**
     * Checks for the existence of a payment method with the same fingerprint for a user.
     *
     * @param userId      The ID of the user.
     * @param fingerprint The fingerprint of the payment method.
     * @return An Optional containing the payment method with the matching fingerprint if it exists.
     */
    Optional<PaymentMethod> findByUserIdAndFingerprint(Long userId, String fingerprint);

    /**
     * Finds a payment method by user ID and payment method ID.
     *
     * @param userId The ID of the user.
     * @param id The ID of the payment method.
     * @return An Optional containing the payment method if found.
     */
    Optional<PaymentMethod> findByUserIdAndId(Long userId, Long id);
}
