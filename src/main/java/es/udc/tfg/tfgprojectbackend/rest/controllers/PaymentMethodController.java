package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.PaymentMethod;
import es.udc.tfg.tfgprojectbackend.model.entities.UserAddress;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.PaymentMethodService;
import es.udc.tfg.tfgprojectbackend.rest.dtos.PaymentMethodDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.UpdatePaymentMethodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.PaymentMethodConversor.toPaymentMethodDto;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.PaymentMethodConversor.toPaymentMethodDtos;

@RestController
@RequestMapping("/paymentMethods")
public class PaymentMethodController {

    @Autowired
    private PaymentMethodService paymentMethodService;


    /**
     * Adds a new payment method for a user.
     *
     * @param userId      the ID of the user.
     * @param paymentMethod the PaymentMethod object containing the details of the new payment method.
     * @return the updated list of payment methods.
     */
    @PostMapping("/add")
    public PaymentMethodDto addPaymentMethod(@RequestAttribute Long userId, @RequestBody PaymentMethodDto paymentMethod)
            throws InstanceNotFoundException, DuplicateInstanceException {

        return toPaymentMethodDto(paymentMethodService.addPaymentMethod(userId, paymentMethod.getStripeId(), paymentMethod.getBrand(),
                paymentMethod.getCountry(), paymentMethod.getExpMonth(), paymentMethod.getExpYear(),
                paymentMethod.getLast4(), paymentMethod.getFunding(), paymentMethod.getFingerprint(),
                paymentMethod.getByDefault()));
    }

    /**
     * Updates an existing payment method for a user.
     *
     * @param userId      the ID of the user.
     * @param methodId    the ID of the payment method to update.
     * @param paymentMethod the PaymentMethod object containing the updated details.
     * @return the updated list of payment methods.
     */
    @PutMapping("/update/{methodId}")
    public PaymentMethodDto updatePaymentMethod(@RequestAttribute Long userId, @PathVariable Long methodId,
                                                   @RequestBody UpdatePaymentMethodDto paymentMethod)
            throws InstanceNotFoundException {

        return toPaymentMethodDto(paymentMethodService.updatePaymentMethod(userId, methodId, paymentMethod.getExpMonth(), paymentMethod.getExpYear(), paymentMethod.getByDefault()));
    }

    /**
     * Removes a payment method for a user.
     *
     * @param userId   the ID of the user.
     * @param methodId the ID of the payment method to remove.
     * @return the updated list of payment methods.
     */
    @DeleteMapping("/remove/{methodId}")
    public List<PaymentMethodDto> removePaymentMethod(@RequestAttribute Long userId, @PathVariable Long methodId)
            throws InstanceNotFoundException {

        return toPaymentMethodDtos(paymentMethodService.removePaymentMethod(userId, methodId));
    }

    /**
     * Retrieves all payment methods for a user.
     *
     * @param userId the ID of the user.
     * @return a list of PaymentMethod entities.
     */
    @GetMapping("/list")
    public List<PaymentMethodDto> findPaymentMethods(@RequestAttribute Long userId) throws InstanceNotFoundException {
        return toPaymentMethodDtos(paymentMethodService.findPaymentMethods(userId));
    }

    /**
     * Retrieves a payment method by its ID.
     *
     * @param methodId the ID of the payment method.
     * @return the PaymentMethod entity.
     */
    @GetMapping("/get/{methodId}")
    public PaymentMethodDto findPaymentMethodById(@RequestAttribute Long userId, @PathVariable Long methodId)
            throws InstanceNotFoundException {
        return toPaymentMethodDto(paymentMethodService.findPaymentMethodById(userId, methodId));
    }

    /**
     * Retrieves the default payment method for a user.
     *
     * @param userId the ID of the user.
     * @return the default PaymentMethod entity.
     */
    @GetMapping("/default")
    public PaymentMethodDto findDefaultPaymentMethod(@RequestAttribute Long userId) {

        PaymentMethod paymentMethod = paymentMethodService.findDefaultPaymentMethod(userId);

        return paymentMethod != null ? toPaymentMethodDto(paymentMethod) : null;
    }

}
