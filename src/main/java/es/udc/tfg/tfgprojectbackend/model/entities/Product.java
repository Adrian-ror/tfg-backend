package es.udc.tfg.tfgprojectbackend.model.entities;

import es.udc.tfg.tfgprojectbackend.model.exceptions.MaxImagesExceededException;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a product in the system.
 */
@Entity
public class Product {

    public static final int MAX_IMAGES = 6;

    private Long id;

    private String name;

    private String description;

    private String shortDescription;

    private BigDecimal price;

    private BigDecimal discount;

    private int stock;

    private BigDecimal rating;

    private Boolean isVisible;


    private Boolean isService;

    private String brand;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private BigDecimal weight;

    private LocalDateTime createdAt;

    private Category category;

    private User user;

    private Set<ProductImage> images = new HashSet<>();

    private Set<ProductReview> reviews = new HashSet<>();


    /**
     * Default constructor.
     */
    public Product() {}

    /**
     * Constructor with parameters.
     *
     * @param name             Name of the product.
     * @param description      Description of the product.
     * @param shortDescription Short description of the product.
     * @param price            Price of the product.
     * @param discount         Discount on the product.
     * @param stock            Stock quantity of the product.
     * @param rating           Rating of the product.
     * @param brand            Brand of the product.
     * @param length           Length of the product.
     * @param width            Width of the product.
     * @param height           Height of the product.
     * @param weight           Weight of the product.
     * @param category         Category of the product.
     * @param user             User associated with the product.
     */
    public Product(String name, String description, String shortDescription, BigDecimal price, BigDecimal discount,
                   int stock, BigDecimal rating, Boolean isVisible , Boolean isService, String brand, BigDecimal length, BigDecimal width,
                   BigDecimal height, BigDecimal weight, LocalDateTime createdAt, Category category, User user) {
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
        this.discount = discount;
        this.stock = stock;
        this.rating = rating;
        this.isVisible = isVisible;
        this.isService = isService;
        this.brand = brand;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.createdAt = createdAt;
        this.category = category;
        this.user = user;
    }

    /**
     * Gets the product ID.
     *
     * @return The product ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the product ID.
     *
     * @param id The product ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the product name.
     *
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param name The product name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the product description.
     *
     * @return The product description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the product description.
     *
     * @param description The product description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the short description of the product.
     *
     * @return The short description.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the short description of the product.
     *
     * @param shortDescription The short description.
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * Gets the product price.
     *
     * @return The product price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param price The product price.
     */
    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Gets the product discount.
     *
     * @return The product discount.
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * Sets the product discount.
     *
     * @param discount The product discount.
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * Gets the stock quantity of the product.
     *
     * @return The stock quantity.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock quantity of the product.
     *
     * @param stock The stock quantity.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets the product rating.
     *
     * @return The product rating.
     */
    public BigDecimal getRating() {
        return rating;
    }

    /**
     * Sets the product rating.
     *
     * @param rating The product rating.
     */
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean visible) {
        isVisible = visible;
    }

    public Boolean getIsService() {
        return isService;
    }

    public void setIsService(Boolean service) {
        isService = service;
    }

    /**
     * Gets the product brand.
     *
     * @return The product brand.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the product brand.
     *
     * @param brand The product brand.
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the length of the product.
     *
     * @return The product length.
     */
    public BigDecimal getLength() {
        return length;
    }

    /**
     * Sets the length of the product.
     *
     * @param length The product length.
     */
    public void setLength(BigDecimal length) {
        this.length = length;
    }

    /**
     * Gets the width of the product.
     *
     * @return The product width.
     */
    public BigDecimal getWidth() {
        return width;
    }

    /**
     * Sets the width of the product.
     *
     * @param width The product width.
     */
    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    /**
     * Gets the height of the product.
     *
     * @return The product height.
     */
    public BigDecimal getHeight() {
        return height;
    }

    /**
     * Sets the height of the product.
     *
     * @param height The product height.
     */
    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * Gets the weight of the product.
     *
     * @return The product weight.
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the product.
     *
     * @param weight The product weight.
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }


    /**
     * Gets the creation date and time of the product.
     *
     * @return The creation date and time.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date and time of the product.
     *
     * @param createdAt The creation date and time.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the category of the product.
     *
     * @return The category of the product.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category of the product.
     *
     * @param category The category of the product.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the user associated with the product.
     *
     * @return The user associated with the product.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the product.
     *
     * @param user The user associated with the product.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the images associated with the product.
     *
     * @return The set of product images.
     */
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public Set<ProductImage> getImages() {
        return images;
    }

    /**
     * Sets the images associated with the product.
     *
     * @param images The set of product images.
     */
    public void setImages(Set<ProductImage> images) {
        this.images = images;
    }


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<ProductReview> getReviews() {
        return reviews;
    }

    public void setReviews(Set<ProductReview> reviews) {
        this.reviews = reviews;
        updateAverageRating();
    }


    /**
     * Adds an image to the product.
     *
     * @param image The product image to be added.
     */
    public void addImage(ProductImage image) throws MaxImagesExceededException {

        if (images.size() >= MAX_IMAGES) {
            throw new MaxImagesExceededException(MAX_IMAGES);
        }

        images.add(image);
        image.setProduct(this);
    }

    /**
     * Adds a review to the product and updates the average rating.
     *
     * @param review The product review to add.
     */
    public void addReview(ProductReview review) {
        reviews.add(review);
        review.setProduct(this);
        updateAverageRating();
    }

    /**
     * Calculates and updates the average rating of the product based on its reviews.
     */
    private void updateAverageRating() {
        BigDecimal totalRating = BigDecimal.ZERO;

        for (ProductReview review : reviews) {
            totalRating = totalRating.add(review.getRating());
        }

        if (!reviews.isEmpty()) {
            this.rating = totalRating.divide(BigDecimal.valueOf(reviews.size()), 2, RoundingMode.HALF_UP);
        } else {
            this.rating = BigDecimal.ZERO;
        }
    }

    public void updateStock(int quantity) {

        if(this.stock > 0){
           setStock(this.stock-quantity);
        }
    }

}
