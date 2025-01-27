package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.PaymentMethod;
import es.udc.tfg.tfgprojectbackend.model.entities.User;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.services.PaymentMethodService;

import es.udc.tfg.tfgprojectbackend.model.entities.PaymentMethodDao;
import es.udc.tfg.tfgprojectbackend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PaymentMethodServiceTest {

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentMethodDao paymentMethodDao;

    private User createUser(String userName) {
        return new User(userName, "password", "FirstName", "LastName", userName + "@example.com", "123456789", null, User.RoleType.CLIENT, User.StatusType.ACTIVE);
    }

    private PaymentMethod createPaymentMethod(User user, String stripeId, String brand, String last4, String fingerprint) {
        return new PaymentMethod(user, stripeId, brand, "US", 12, 2025, last4, "credit", fingerprint, true);
    }

    @Test
    public void testAddPaymentMethod() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUser("user1");
        userService.signUp(user);

        paymentMethodService.addPaymentMethod(user.getId(), "stripeId123", "Visa", "US", 12, 2025, "1234", "credit", "fingerprint", true);

        PaymentMethod paymentMethod = paymentMethodDao.findByUserId(user.getId()).get(0);
        assertNotNull(paymentMethod);
        assertEquals("Visa", paymentMethod.getBrand());
        assertEquals("1234", paymentMethod.getLast4());
    }

    @Test
    public void testAddDuplicatePaymentMethod() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUser("user2");
        userService.signUp(user);

        PaymentMethod paymentMethod1 = createPaymentMethod(user, "stripeId123", "Visa", "1234", "1");
        paymentMethodService.addPaymentMethod(user.getId(), paymentMethod1.getStripeId(), paymentMethod1.getBrand(),
                paymentMethod1.getCountry(), paymentMethod1.getExpMonth(), paymentMethod1.getExpYear(), paymentMethod1.getLast4(),
                paymentMethod1.getFunding(), paymentMethod1.getFingerprint(), paymentMethod1.getByDefault());

        assertThrows(DuplicateInstanceException.class, () -> paymentMethodService.addPaymentMethod(user.getId(),
                "stripeId123", "Visa", "US", 12, 2025, "1234", "credit", "1", true));
    }

    @Test
    public void testUpdatePaymentMethod() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUser("user3");
        userService.signUp(user);

        PaymentMethod paymentMethod = createPaymentMethod(user, "stripeId123", "Visa", "1234", "1");
        PaymentMethod payment = paymentMethodService.addPaymentMethod(user.getId(), paymentMethod.getStripeId(), paymentMethod.getBrand(),
                paymentMethod.getCountry(), paymentMethod.getExpMonth(), paymentMethod.getExpYear(), paymentMethod.getLast4(),
                paymentMethod.getFunding(), paymentMethod.getFingerprint(), paymentMethod.getByDefault());

        PaymentMethod updatedMethod = paymentMethodService.updatePaymentMethod(user.getId(), payment.getId(),
                6, 2027, true);

        assertEquals(6, updatedMethod.getExpMonth());
        assertEquals(2027, updatedMethod.getExpYear());
        assertTrue(updatedMethod.getByDefault());
    }

    @Test
    public void testUpdateNonExistentPaymentMethod() throws DuplicateInstanceException {
        User user = createUser("user4");
        userService.signUp(user);

        assertThrows(InstanceNotFoundException.class, () -> paymentMethodService.updatePaymentMethod(user.getId(), 999L, 6, 2027, true));
    }

    @Test
    public void testRemovePaymentMethod() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUser("user5");
        userService.signUp(user);

        PaymentMethod paymentMethod = createPaymentMethod(user, "stripeId123", "Visa","1234", "1");
        PaymentMethod payment = paymentMethodService.addPaymentMethod(user.getId(), paymentMethod.getStripeId(), paymentMethod.getBrand(),
                paymentMethod.getCountry(), paymentMethod.getExpMonth(), paymentMethod.getExpYear(), paymentMethod.getLast4(),
                paymentMethod.getFunding(), paymentMethod.getFingerprint(), paymentMethod.getByDefault());

        paymentMethodService.removePaymentMethod(user.getId(), payment.getId());
        assertTrue(paymentMethodDao.findByUserId(user.getId()).isEmpty());
    }

    @Test
    public void testRemoveNonExistentPaymentMethod() throws DuplicateInstanceException {
        User user = createUser("user6");
        userService.signUp(user);

        assertThrows(InstanceNotFoundException.class, () -> paymentMethodService.removePaymentMethod(user.getId(), 999L));
    }

    @Test
    public void testFindPaymentMethods() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUser("user7");
        userService.signUp(user);

        PaymentMethod paymentMethod1 = createPaymentMethod(user, "stripeId123", "Visa", "1234", "1");
        PaymentMethod paymentMethod2 = createPaymentMethod(user, "stripeId124", "MasterCard", "4334", "2");

        paymentMethodService.addPaymentMethod(user.getId(), paymentMethod1.getStripeId(), paymentMethod1.getBrand(),
                paymentMethod1.getCountry(), paymentMethod1.getExpMonth(), paymentMethod1.getExpYear(), paymentMethod1.getLast4(),
                paymentMethod1.getFunding(), paymentMethod1.getFingerprint(), paymentMethod1.getByDefault());
        paymentMethodService.addPaymentMethod(user.getId(), paymentMethod2.getStripeId(), paymentMethod2.getBrand(),
                paymentMethod2.getCountry(), paymentMethod2.getExpMonth(), paymentMethod2.getExpYear(), paymentMethod2.getLast4(),
                paymentMethod2.getFunding(), paymentMethod2.getFingerprint(), paymentMethod2.getByDefault());

        assertEquals(2, paymentMethodDao.findByUserId(user.getId()).size());
    }

    @Test
    public void testFindPaymentMethodById() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUser("user8");
        userService.signUp(user);

        PaymentMethod paymentMethod = createPaymentMethod(user, "stripeId123", "Visa", "1234", "1");
        PaymentMethod payment= paymentMethodService.addPaymentMethod(user.getId(), paymentMethod.getStripeId(), paymentMethod.getBrand(),
                paymentMethod.getCountry(), paymentMethod.getExpMonth(), paymentMethod.getExpYear(), paymentMethod.getLast4(),
                paymentMethod.getFunding(), paymentMethod.getFingerprint(), paymentMethod.getByDefault());

        PaymentMethod foundMethod = paymentMethodService.findPaymentMethodById(user.getId(), payment.getId());
        assertEquals(payment.getId(), foundMethod.getId());
    }

    @Test
    public void testFindPaymentMethodByIdNotFound() throws DuplicateInstanceException {
        User user = createUser("user9");
        userService.signUp(user);

        assertThrows(InstanceNotFoundException.class, () -> paymentMethodService.findPaymentMethodById(user.getId(), 999L));
    }
}
