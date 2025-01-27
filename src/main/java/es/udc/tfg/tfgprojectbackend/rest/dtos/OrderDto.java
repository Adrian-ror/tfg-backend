package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {

    private Long id;
    private List<OrderItemDto> items;
    private long date;
    private BigDecimal totalPrice;
    private String postalAddress;
    private String postalCode;
    private String state;
    private Long paymentMethodId;
    private String brand;
    private String last4;
    private String funding;
    private String shippingMethodName;
    private String estimatedDeliveryTime;

    public OrderDto() {}

    public OrderDto(Long id, List<OrderItemDto> items, long date, BigDecimal totalPrice, String postalAddress,
                    String postalCode, String state, Long paymentMethodId, String brand, String last4,
                    String funding, String shippingMethodName, String estimatedDeliveryTime) {
        this.id = id;
        this.items = items;
        this.date = date;
        this.totalPrice = totalPrice;
        this.postalAddress = postalAddress;
        this.postalCode = postalCode;
        this.state = state;
        this.paymentMethodId = paymentMethodId;
        this.brand = brand;
        this.last4 = last4;
        this.funding = funding;
        this.shippingMethodName = shippingMethodName;
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getFunding() {
        return funding;
    }

    public void setFunding(String funding) {
        this.funding = funding;
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
}
