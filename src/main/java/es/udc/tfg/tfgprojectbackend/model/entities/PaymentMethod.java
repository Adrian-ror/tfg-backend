package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.*;

/**
 * Class that represents a payment method associated with a user.
 */
@Entity
public class PaymentMethod {

    private Long id;
    private User user;
    private String stripeId;
    private String brand;
    private String country;
    private int expMonth;
    private int expYear;
    private String last4;
    private String funding;
    private String fingerprint;
    private Boolean byDefault;

    /**
     * Default constructor for JPA.
     */
    public PaymentMethod() {}

    /**
     * Constructor that initializes a PaymentMethod object with the provided parameters.
     *
     * @param user        The user associated with this payment method.
     * @param stripeId    The ID of the card in Stripe.
     * @param brand       The brand of the card.
     * @param country     The country code.
     * @param expMonth    The expiration month of the card.
     * @param expYear     The expiration year of the card.
     * @param last4       The last 4 digits of the card.
     * @param funding     The funding type of the card.
     * @param fingerprint The fingerprint of the card.
     * @param byDefault   Indicates whether this payment method is the default one.
     */
    public PaymentMethod(User user, String stripeId, String brand, String country,
                         int expMonth, int expYear, String last4, String funding,
                         String fingerprint, Boolean byDefault) {
        this.user = user;
        this.stripeId = stripeId;
        this.brand = brand;
        this.country = country;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.last4 = last4;
        this.funding = funding;
        this.fingerprint = fingerprint;
        this.byDefault = byDefault;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "stripeId", nullable = false)
    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "exp_month", nullable = false)
    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    @Column(name = "exp_year", nullable = false)
    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }

    @Column(name = "last4", nullable = false)
    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    @Column(name = "funding")
    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    @Column(name = "fingerprint")
    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Column(name = "byDefault", nullable = false)
    public Boolean getByDefault() {
        return byDefault;
    }

    public void setByDefault(Boolean byDefault) {
        this.byDefault = byDefault;
    }
}
