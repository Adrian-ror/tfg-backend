package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.math.BigDecimal;

public class ProductSummaryDto {

    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String categoryName;
    private String shortDescription;
    private BigDecimal rating;
    private Boolean isVisible;
    private Boolean isService;

    public ProductSummaryDto() {}

    public ProductSummaryDto(Long id, String name, String image, BigDecimal price, String categoryName, String shortDescription, BigDecimal rating, Boolean isVisible , Boolean isService) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.categoryName = categoryName;
        this.shortDescription = shortDescription;
        this.rating = rating;
        this.isVisible = isVisible;
        this.isService = isService;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
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
}
