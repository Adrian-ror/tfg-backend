package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long id;
    private Long productId;
    private String productName;
    private String categoryName;
    private BigDecimal productPrice;
    private int quantity;

    public OrderItemDto() {}

    public OrderItemDto(Long id, Long productId, String productName, String categoryName, BigDecimal productPrice, int quantity) {

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.productPrice = productPrice;
        this.quantity = quantity;

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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
