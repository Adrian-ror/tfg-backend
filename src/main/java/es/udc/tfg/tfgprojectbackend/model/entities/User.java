package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

/**
 * User entity class
 *
 * Represents a user in the system with various attributes
 * such as username, password, personal information, role, and status.
 *
 * @version 0.1.0
 */
@Entity
public class User {

    /**
     * RoleType enum
     *
     * Defines the possible roles a user can have in the system.
     */
    public enum RoleType {CLIENT, PROVIDER, ADMIN}

    /**
     * StatusType enum
     *
     * Defines the possible statuses of a user account.
     */
    public enum StatusType {ACTIVE, BANNED}

    /**
     * User attributes
     */
    private Long id;                // Unique identifier for the user
    private String userName;        // Unique username for the user
    private String password;        // User's password
    private String firstName;       // User's first name
    private String lastName;        // User's last name
    private String email;           // User's email address
    private String phoneNumber;     // User's phone number
    private String image;           // URL to the user's profile image
    private RoleType role;          // User's role in the system
    private StatusType status;      // User's account status
    private ShoppingCart shoppingCart; // User's shopping cart
    private WishList wishList;   // User's wishlist

    /**
     * Default constructor
     */
    public User() {}

    /**
     * Constructor with parameters
     *
     * @param userName    the username of the user
     * @param password    the password of the user
     * @param firstName   the first name of the user
     * @param lastName    the last name of the user
     * @param email       the email of the user
     * @param phoneNumber the phone number of the user
     * @param image       the profile image URL of the user
     * @param role        the role of the user
     * @param status      the status of the user account
     */
    public User(String userName, String password, String firstName, String lastName,
                String email, String phoneNumber, String image, RoleType role, StatusType status) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.role = role;
        this.status = status;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return the user ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username
     */
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the profile image URL of the user.
     *
     * @return the image URL
     */
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user
     */
    @Enumerated(EnumType.STRING)
    public RoleType getRole() {
        return role;
    }
    public void setRole(RoleType role) {
        this.role = role;
    }

    /**
     * Gets the status of the user account.
     *
     * @return the account status
     */
    @Enumerated(EnumType.STRING)
    public StatusType getStatus() {
        return status;
    }
    public void setStatus(StatusType status) {
        this.status = status;
    }

    /**
     * Gets the shopping cart associated with the user.
     *
     * @return the shopping cart
     */
    @OneToOne(mappedBy = "user", optional = false, fetch = FetchType.LAZY)
    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    /**
     * Gets the wishlist associated with the user.
     *
     * @return the wishlist
     */
    @OneToOne(mappedBy = "user", optional = false, fetch = FetchType.LAZY)
    public WishList getWishList() {
        return wishList;
    }
    public void setWishList(WishList wishList) {
        this.wishList = wishList;
    }
}
