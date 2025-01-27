package es.udc.tfg.tfgprojectbackend.rest.dtos;

import jakarta.validation.constraints.NotBlank;

public class UpdatePaymentMethodDto {

    @NotBlank
    private int expMonth;

    @NotBlank
    private int expYear;

    @NotBlank
    private Boolean byDefault;

    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    public Boolean getByDefault() {
        return byDefault;
    }

    public void setByDefault(Boolean byDefault) {
        this.byDefault = byDefault;
    }
}
