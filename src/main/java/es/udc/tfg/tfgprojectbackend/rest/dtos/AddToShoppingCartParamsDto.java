package es.udc.tfg.tfgprojectbackend.rest.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class AddToShoppingCartParamsDto {

    private Long productId;
    private int quantity;

    @NotNull
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Min(value=1)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}