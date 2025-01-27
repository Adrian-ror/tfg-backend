package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private String shortDescription;
    private BigDecimal price;
    private BigDecimal discount;
    private int stock;
    private BigDecimal rating;
    private int numRatings;
    private Boolean isVisible;
    private Boolean isService;
    private String brand;
    private BigDecimal length;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal weight;
    private Long createdAt;
    private Long categoryId;
    private String categoryName;
    private Long userId;
    private String userName;
    private String[] images;

    public ProductDto() {}

    public ProductDto(Long id, String name, String description, String shortDescription, BigDecimal price,
                      BigDecimal discount, int stock, BigDecimal rating, int numRatings, Boolean isVisible, String brand, Boolean isService,
                      BigDecimal length, BigDecimal width, BigDecimal height, BigDecimal weight,
                      Long createdAt, Long categoryId, String categoryName, Long userId, String userName,
                      String[] images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.shortDescription = shortDescription;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.rating = rating;
        this.numRatings = numRatings;
        this.isVisible = isVisible;
        this.isService = isService;
        this.brand = brand;
        this.length = length;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.userId = userId;
        this.userName = userName;
        this.images = images;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(int numRatings) {
        this.numRatings = numRatings;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Boolean getService() {
        return isService;
    }

    public void setService(Boolean service) {
        isService = service;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

}
