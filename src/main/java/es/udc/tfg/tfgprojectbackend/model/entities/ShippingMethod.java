package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * Represents a shipping method used for order deliveries.
 */
@Entity
public class ShippingMethod {

    private Long id;
    private String name;
    private String description;
    private BigDecimal shippingCost;
    private String estimatedDeliveryTime;

    public ShippingMethod() {}

    /**
     * Constructs a new ShippingMethod instance.
     *
     * @param name                  the name of the shipping method
     * @param description           the description of the shipping method
     * @param shippingCost          the cost of shipping
     * @param estimatedDeliveryTime  the estimated delivery time
     */
    public ShippingMethod(String name, String description, BigDecimal shippingCost, String estimatedDeliveryTime) {
        this.name = name;
        this.description = description;
        this.shippingCost = shippingCost;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "shippingCost", nullable = false)
    public BigDecimal getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(BigDecimal shippingCost) {
        this.shippingCost = shippingCost;
    }

    @Column(name = "estimatedDeliveryTime")
    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}
