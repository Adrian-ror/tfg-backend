package es.udc.tfg.tfgprojectbackend.rest.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentMethodDto {

    @NotNull
    private Long id;

    @NotBlank
    private String stripeId;

    @NotBlank
    private String brand;

    @NotBlank
    private String country;

    @NotNull
    private int expMonth;

    @NotNull
    private int expYear;

    @NotBlank
    private String last4;

    @NotBlank
    private String funding;

    @NotBlank
    private String fingerprint;

    private Boolean byDefault;

    @NotNull
    private Long userId;

    public PaymentMethodDto() {
    }

    public PaymentMethodDto(Long id, String stripeId, String brand, String country, int expMonth, int expYear, String last4, String funding, String fingerprint, Boolean byDefault, Long userId) {
        this.id = id;
        this.stripeId = stripeId;
        this.brand = brand;
        this.country = country;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.last4 = last4;
        this.funding = funding;
        this.fingerprint = fingerprint;
        this.byDefault = byDefault;
        this.userId = userId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Boolean getByDefault() {
        return byDefault;
    }

    public void setByDefault(Boolean byDefault) {
        this.byDefault = byDefault;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
