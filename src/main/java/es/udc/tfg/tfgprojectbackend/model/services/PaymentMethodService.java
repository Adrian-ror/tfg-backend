package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.PaymentMethod;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;

import java.util.List;

/**
 * Service interface for managing payment methods for users.
 */
public interface PaymentMethodService {

    /**
     * Adds a new payment method for a user and returns the updated list of payment methods.
     *
     * @param userId      the ID of the user.
     * @param stripeId    the unique ID of the card in Stripe.
     * @param brand       the brand of the card (e.g., Visa, MasterCard).
     * @param country     the country code of the card.
     * @param expMonth    the expiration month of the card.
     * @param expYear     the expiration year of the card.
     * @param last4       the last 4 digits of the card.
     * @param funding     the funding type of the card (e.g., credit, debit).
     * @param fingerprint the fingerprint of the card for verification.
     * @param byDefault   whether to set this payment method as the default.
     * @return the updated list of PaymentMethod entities.
     * @throws InstanceNotFoundException if the user does not exist.
     * @throws DuplicateInstanceException if the payment method already exists for the user.
     */
    PaymentMethod addPaymentMethod(Long userId, String stripeId, String brand, String country,
                                   int expMonth, int expYear, String last4, String funding,
                                   String fingerprint, Boolean byDefault)
            throws InstanceNotFoundException, DuplicateInstanceException;

    /**
     * Updates an existing payment method and returns the updated PaymentMethod.
     *
     * @param userId      the ID of the user.
     * @param methodId    the ID of the payment method to update.
     * @param expMonth    the expiration month of the card.
     * @param expYear     the expiration year of the card.
     * @param byDefault   whether to set this as the default payment method.
     * @return the updated PaymentMethod entity.
     * @throws InstanceNotFoundException if the user or payment method does not exist.
     */
    PaymentMethod updatePaymentMethod(Long userId, Long methodId, int expMonth, int expYear, Boolean byDefault)
            throws InstanceNotFoundException;

    /**
     * Removes a payment method from a user’s account and returns the updated list of payment methods.
     *
     * @param userId   the ID of the user.
     * @param methodId the ID of the payment method to remove.
     * @return the updated list of PaymentMethod entities.
     * @throws InstanceNotFoundException if the user or payment method does not exist.
     */
    List<PaymentMethod> removePaymentMethod(Long userId, Long methodId)
            throws InstanceNotFoundException;

    /**
     * Retrieves the default payment method for a user.
     *
     * @param userId the ID of the user.
     * @return the default PaymentMethod entity, or null if none exists.
     */
    PaymentMethod findDefaultPaymentMethod(Long userId);

    /**
     * Retrieves a payment method by its ID.
     *
     * @param methodId the ID of the payment method.
     * @return the PaymentMethod entity.
     * @throws InstanceNotFoundException if the payment method does not exist.
     */
    PaymentMethod findPaymentMethodById(Long userId, Long methodId) throws InstanceNotFoundException;

    /**
     * Lists all payment methods associated with a user.
     *
     * @param userId the ID of the user.
     * @return a list of PaymentMethod entities.
     * @throws InstanceNotFoundException if the user does not exist.
     */
    List<PaymentMethod> findPaymentMethods(Long userId) throws InstanceNotFoundException;
}
