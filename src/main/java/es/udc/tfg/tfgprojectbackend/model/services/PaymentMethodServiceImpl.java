package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.PaymentMethod;
import es.udc.tfg.tfgprojectbackend.model.entities.PaymentMethodDao;
import es.udc.tfg.tfgprojectbackend.model.entities.User;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Implementation of the PaymentMethodService interface.
 * This service handles operations related to payment methods.
 */
@Service
@Transactional
public class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    /**
     * Adds a new payment method for a user.
     *
     * @param userId      The ID of the user for whom the payment method is being added.
     * @param stripeId    The unique identifier from Stripe for the payment method.
     * @param brand       The brand of the payment method (e.g., Visa, MasterCard).
     * @param country     The country associated with the payment method.
     * @param expMonth    The expiration month of the payment method.
     * @param expYear     The expiration year of the payment method.
     * @param last4       The last four digits of the payment method.
     * @param funding     The funding source type (e.g., credit, debit).
     * @param fingerprint A unique fingerprint for the payment method.
     * @param byDefault   Indicates whether this payment method should be set as the default.
     * @return A list of payment methods associated with the user.
     * @throws InstanceNotFoundException         If the user does not exist.
     * @throws DuplicateInstanceException If the payment method already exists for the user.
     */
    @Override
    public PaymentMethod addPaymentMethod(Long userId, String stripeId, String brand, String country,
                                                int expMonth, int expYear, String last4, String funding,
                                                String fingerprint, Boolean byDefault)
            throws InstanceNotFoundException, DuplicateInstanceException {

        User user = permissionChecker.checkUser(userId);

        if (paymentMethodDao.findByUserIdAndFingerprint(userId, fingerprint).isPresent()) {
            throw new DuplicateInstanceException("project.entities.paymentMethod", last4);
        }

        PaymentMethod newPaymentMethod = new PaymentMethod(user, stripeId, brand, country, expMonth, expYear, last4, funding, fingerprint, byDefault);

        if (byDefault) {
            paymentMethodDao.findByUserIdAndByDefaultTrue(userId)
                    .ifPresent(method -> method.setByDefault(false));
        }

        paymentMethodDao.save(newPaymentMethod);

        return newPaymentMethod;
    }

    /**
     * Updates an existing payment method for a user.
     *
     * @param userId      The ID of the user updating the payment method.
     * @param methodId    The ID of the payment method to update.
     * @param expMonth    The updated expiration month of the payment method.
     * @param expYear     The updated expiration year of the payment method.
     * @param byDefault   Indicates whether this payment method should be set as the default.
     * @return A list of payment methods associated with the user.
     * @throws InstanceNotFoundException If the user does not exist or the payment method is not found.
     */
    @Override
    public PaymentMethod updatePaymentMethod(Long userId, Long methodId, int expMonth, int expYear, Boolean byDefault)
            throws InstanceNotFoundException {

        permissionChecker.checkUser(userId);

        PaymentMethod paymentMethod = paymentMethodDao.findByUserIdAndId(userId, methodId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.paymentMethod", methodId));

        paymentMethod.setExpMonth(expMonth);
        paymentMethod.setExpYear(expYear);

        if (byDefault && !paymentMethod.getByDefault()) {
            paymentMethodDao.findByUserIdAndByDefaultTrue(userId)
                    .ifPresent(method -> method.setByDefault(false));
        }

        paymentMethod.setByDefault(byDefault);
        paymentMethodDao.save(paymentMethod);

        return paymentMethod;
    }

    /**
     * Removes a payment method for a user.
     *
     * @param userId   The ID of the user removing the payment method.
     * @param methodId The ID of the payment method to remove.
     * @return A list of payment methods associated with the user after removal.
     * @throws InstanceNotFoundException If the user does not exist or the payment method is not found.
     */
    @Override
    public List<PaymentMethod> removePaymentMethod(Long userId, Long methodId)
            throws InstanceNotFoundException {

        permissionChecker.checkUser(userId);

        PaymentMethod paymentMethod = paymentMethodDao.findByUserIdAndId(userId, methodId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.paymentMethod", methodId));

        paymentMethodDao.delete(paymentMethod);

        return paymentMethodDao.findByUserId(userId);
    }

    /**
     * Finds the default payment method for a user.
     *
     * @param userId The ID of the user for whom to find the default payment method.
     * @return The default payment method, or null if none exists.
     */
    @Override
    public PaymentMethod findDefaultPaymentMethod(Long userId) {
        return paymentMethodDao.findByUserIdAndByDefaultTrue(userId).orElse(null);
    }

    /**
     * Finds a payment method by its ID.
     *
     * @param methodId The ID of the payment method to find.
     * @return The found payment method.
     * @throws InstanceNotFoundException If the payment method is not found.
     */
    @Override
    public PaymentMethod findPaymentMethodById(Long userId, Long methodId) throws InstanceNotFoundException {
        return paymentMethodDao.findByUserIdAndId( userId, methodId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.paymentMethod", methodId));
    }

    /**
     * Finds all payment methods associated with a user.
     *
     * @param userId The ID of the user for whom to find payment methods.
     * @return A list of payment methods associated with the user.
     * @throws InstanceNotFoundException If the user does not exist.
     */
    @Override
    public List<PaymentMethod> findPaymentMethods(Long userId) throws InstanceNotFoundException {
        permissionChecker.checkUser(userId);
        return paymentMethodDao.findByUserId(userId);
    }
}
