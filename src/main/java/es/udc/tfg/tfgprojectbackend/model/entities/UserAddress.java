package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.*;

/**
 * Represents a user's address.
 */
@Entity
public class UserAddress {

    private Long id;
    private User user;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private boolean isDefault;

    public UserAddress() {}

    /**
     * Constructs a new UserAddress instance.
     *
     * @param user          the user associated with the address
     * @param addressLine1  the first line of the address
     * @param addressLine2  the second line of the address (optional)
     * @param city          the city of the address
     * @param state         the state of the address
     * @param postalCode    the postal code of the address
     * @param country       the country of the address
     * @param phoneNumber   the phone number associated with the address (optional)
     * @param isDefault     indicates if this is the default address for the user
     */
    public UserAddress(User user, String addressLine1, String addressLine2, String city, String state,
                       String postalCode, String country, String phoneNumber, boolean isDefault) {
        this.user = user;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.isDefault = isDefault;
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

    @Column(name = "addressLine1", nullable = false)
    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Column(name = "addressLine2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Column(name = "city", nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "state", nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "postalCode", nullable = false)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "country", nullable = false)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "isDefault", nullable = false)
    public boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}
