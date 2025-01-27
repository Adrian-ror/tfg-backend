package es.udc.tfg.tfgprojectbackend.rest.dtos;

import jakarta.validation.constraints.NotNull;

public class AddToWishListParamsDto {

    private Long productId;

    @NotNull
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

}