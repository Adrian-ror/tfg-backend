package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.Product;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.MaxImagesExceededException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PrimaryImageNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.PermissionException;

import java.math.BigDecimal;
import java.util.List;

public interface SellerService {

    /**
     * Posts a new product by the specified user.
     *
     * @param userId the ID of the user posting the product
     * @param name the name of the product
     * @param description the full description of the product
     * @param shortDescription a brief description of the product
     * @param price the price of the product
     * @param discount the discount on the product
     * @param stock the available stock for the product
     * @param rating the rating of the product
     * @param isService whether the product is a service
     * @param brand the brand of the product
     * @param length the length of the product
     * @param width the width of the product
     * @param height the height of the product
     * @param weight the weight of the product
     * @param categoryId the ID of the category to which the product belongs
     * @param images an array of image URLs for the product
     * @return the created Product
     * @throws InstanceNotFoundException if the user or category does not exist
     * @throws MaxImagesExceededException if the number of images exceeds the allowed limit
     * @throws PrimaryImageNotFoundException if no primary image is provided
     */
    Product post(Long userId, String name, String description, String shortDescription,
                 BigDecimal price, BigDecimal discount, int stock, BigDecimal rating, Boolean isService,
                 String brand, BigDecimal length, BigDecimal width, BigDecimal height,
                 BigDecimal weight, Long categoryId, String[] images)
            throws InstanceNotFoundException, MaxImagesExceededException, PrimaryImageNotFoundException;

    /**
     * Updates an existing product.
     *
     * @param userId the ID of the user updating the product
     * @param productId the ID of the product to update
     * @param name the new name of the product
     * @param description the new full description of the product
     * @param shortDescription the new brief description of the product
     * @param price the new price of the product
     * @param discount the new discount on the product
     * @param stock the new available stock for the product
     * @param rating the new rating of the product
     * @param isService whether the product is a service
     * @param brand the new brand of the product
     * @param length the new length of the product
     * @param width the new width of the product
     * @param height the new height of the product
     * @param weight the new weight of the product
     * @param categoryId the ID of the new category to which the product will belong
     * @param images an array of new image URLs for the product
     * @return the updated Product
     * @throws InstanceNotFoundException if the product or category does not exist
     * @throws MaxImagesExceededException if the number of images exceeds the allowed limit
     * @throws PrimaryImageNotFoundException if no primary image is provided
     * @throws PermissionException if the user does not have permission to update the product
     */
    Product update(Long userId, Long productId, String name, String description, String shortDescription,
                   BigDecimal price, BigDecimal discount, int stock, BigDecimal rating, Boolean isService,
                   String brand, BigDecimal length, BigDecimal width, BigDecimal height,
                   BigDecimal weight, Long categoryId, String[] images)
            throws InstanceNotFoundException, MaxImagesExceededException, PrimaryImageNotFoundException, PermissionException;

    /**
     * Deletes a product if it belongs to the user and has not been purchased.
     *
     * @param userId the ID of the user deleting the product
     * @param productId the ID of the product to delete
     * @throws InstanceNotFoundException if the product does not exist
     * @throws PermissionException if the user does not have permission to delete the product
     */
    void delete(Long userId, Long productId) throws InstanceNotFoundException, PermissionException;

    /**
     * Retrieves all products posted by the specified user.
     *
     * @param userId the ID of the user whose products to retrieve
     * @return a list of products posted by the user
     * @throws InstanceNotFoundException if the user does not exist
     */
    List<Product> getAllProducts(Long userId) throws InstanceNotFoundException;

    /**
     * Retrieves all products sold by the specified user.
     *
     * @param userId the ID of the user whose sold products to retrieve
     * @return a list of products sold by the user
     * @throws InstanceNotFoundException if the user does not exist
     */
    List<Product> getAllSoldProducts(Long userId) throws InstanceNotFoundException;

    /**
     * Retrieves a specific product by its ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the Product object
     * @throws InstanceNotFoundException if the product does not exist
     * @throws PermissionException if the user does not have permission to view the product
     */
    Product getProductById(Long userId, Long productId) throws InstanceNotFoundException, PermissionException;
}

