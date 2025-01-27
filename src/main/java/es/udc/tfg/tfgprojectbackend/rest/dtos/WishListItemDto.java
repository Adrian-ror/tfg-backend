package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.math.BigDecimal;

public class WishListItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private String shortDescription;
    private Long categoryId;
    private Long addedDate;
    private String image;
    private BigDecimal productPrice;
    private BigDecimal rating;
    private int numRatings;


    public WishListItemDto() {}

    public WishListItemDto(Long id, Long productId, String productName, String shortDescription, Long categoryId, Long addedDate, String image, BigDecimal productPrice
    , BigDecimal rating, int numRatings) {

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.categoryId = categoryId;
        this.addedDate = addedDate;
        this.image = image;
        this.productPrice = productPrice;
        this.rating = rating;
        this.numRatings = numRatings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Long addedDate) {
        this.addedDate = addedDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
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
}

