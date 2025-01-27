package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMethodConversor {

    public final static PaymentMethodDto toPaymentMethodDto(PaymentMethod paymentMethod) {

        return new PaymentMethodDto(paymentMethod.getId(), paymentMethod.getStripeId(),
                paymentMethod.getBrand(), paymentMethod.getCountry(), paymentMethod.getExpMonth(), paymentMethod.getExpYear(),
                paymentMethod.getLast4(), paymentMethod.getFunding(), paymentMethod.getFingerprint(), paymentMethod.getByDefault(), paymentMethod.getUser().getId());
    }


    public final static List<PaymentMethodDto> toPaymentMethodDtos(List<PaymentMethod> paymentMethods) {
        return paymentMethods.stream()
                .map(PaymentMethodConversor::toPaymentMethodDto)
                .collect(Collectors.toList());
    }

}