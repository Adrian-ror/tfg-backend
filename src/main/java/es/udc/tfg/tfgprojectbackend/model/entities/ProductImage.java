package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.*;

/**
 * ProductImage entity class representing an image associated with a product.
 * @version 0.1.0
 */
@Entity
public class ProductImage {

    private Long id;
    private String imageUrl;
    private boolean isPrimary;
    private Product product;

    /**
     * Default constructor.
     */
    public ProductImage() {}

    /**
     * Constructor with parameters.
     *
     * @param imageUrl the URL of the image.
     * @param product the product associated with this image.
     * @param isPrimary whether this image is the primary image for the product.
     */
    public ProductImage(String imageUrl, Product product, boolean isPrimary) {
        this.imageUrl = imageUrl;
        this.product = product;
        this.isPrimary = isPrimary;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
