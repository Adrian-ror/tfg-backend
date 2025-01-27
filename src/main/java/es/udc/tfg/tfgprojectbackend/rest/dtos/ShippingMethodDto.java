package es.udc.tfg.tfgprojectbackend.rest.dtos;
import java.math.BigDecimal;

public class ShippingMethodDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal shippingCost;
    private String estimatedDeliveryTime;

    public ShippingMethodDto() {}

    public ShippingMethodDto(Long id, String name, String description, BigDecimal shippingCost, String estimatedDeliveryTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.shippingCost = shippingCost;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}
