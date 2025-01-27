package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductReviewDto {

    private Long id;
    private Long productId;
    private Long userId;
    private String userName;
    private BigDecimal rating;
    private String comment;
    private Long reviewDate;

    public ProductReviewDto() {}

    public ProductReviewDto(Long id, Long productId, Long userId, String userName, BigDecimal rating, String comment, Long reviewDate) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
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

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Long reviewDate) {
        this.reviewDate = reviewDate;
    }
}
