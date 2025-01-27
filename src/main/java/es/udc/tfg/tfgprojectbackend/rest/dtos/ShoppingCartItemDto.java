package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.math.BigDecimal;

public class ShoppingCartItemDto {

    private Long productId;
    private String productName;
    private String categoryName;
    private String image;
    private BigDecimal productPrice;
    private int quantity;

    public ShoppingCartItemDto() {}

    public ShoppingCartItemDto(Long productId, String productName, String categoryName, String image,BigDecimal productPrice,
                               int quantity) {

        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.image = image;
        this.productPrice = productPrice;
        this.quantity = quantity;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}