package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static es.udc.tfg.tfgprojectbackend.model.entities.Product.MAX_IMAGES;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    private PermissionChecker permissionChecker;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImageDao productImageDao;
    @Autowired
    private OrderItemDao orderItemDao;

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
    @Override
    public Product post(Long userId, String name, String description, String shortDescription,
                        BigDecimal price, BigDecimal discount, int stock, BigDecimal rating,
                        Boolean isService, String brand, BigDecimal length, BigDecimal width, BigDecimal height,
                        BigDecimal weight, Long categoryId, String[] images)
            throws InstanceNotFoundException, MaxImagesExceededException, PrimaryImageNotFoundException {

        User user = permissionChecker.checkUser(userId);
        Category category = getCategoryById(categoryId);

        Product product = new Product(name, description, shortDescription, price, discount, stock, rating, true, isService, brand,
                length, width, height, weight, LocalDateTime.now(), category, user);

        productDao.save(product);
        manageProductImages(product, images);
        return product;
    }

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
     */
    @Override
    public Product update(Long userId, Long productId, String name, String description, String shortDescription,
                          BigDecimal price, BigDecimal discount, int stock, BigDecimal rating, Boolean isService,
                          String brand, BigDecimal length, BigDecimal width, BigDecimal height,
                          BigDecimal weight, Long categoryId, String[] images)
            throws InstanceNotFoundException, MaxImagesExceededException, PrimaryImageNotFoundException, PermissionException {

        permissionChecker.checkUser(userId);
        Product product = permissionChecker.checkProductExistsAndBelongsTo(productId, userId);
        Category category = getCategoryById(categoryId);

        updateProductFields(product, name, description, shortDescription, price, discount, stock, rating, isService, brand,
                length, width, height, weight, category);

        clearProductImages(product);
        manageProductImages(product, images);

        productDao.save(product);
        return product;
    }

    /**
     * Marks a product as invisible if it belongs to the specified user and has not been purchased.
     * This method does not physically delete the product but updates its visibility status.
     *
     * @param userId    the ID of the user attempting to hide the product
     * @param productId the ID of the product to mark as invisible
     * @throws InstanceNotFoundException if the product does not exist
     * @throws PermissionException       if the user does not have permission to hide the product
     */
    @Override
    public void delete(Long userId, Long productId) throws InstanceNotFoundException, PermissionException {
        permissionChecker.checkUser(userId);
        Product product = permissionChecker.checkProductExistsAndBelongsTo(productId, userId);

        product.setIsVisible(false);
        productDao.save(product);
    }

    /**
     * Retrieves all products posted by the specified user.
     *
     * @param userId the ID of the user whose products to retrieve
     * @return a list of products posted by the user
     * @throws InstanceNotFoundException if the user does not exist
     */
    @Override
    public List<Product> getAllProducts(Long userId) throws InstanceNotFoundException {
        User user = permissionChecker.checkUser(userId);
        return productDao.findAllByUser(user);
    }

    /**
     * Retrieves all products sold by the specified user.
     *
     * @param userId the ID of the user whose sold products to retrieve
     * @return a list of products sold by the user
     * @throws InstanceNotFoundException if the user does not exist
     */
    @Override
    public List<Product> getAllSoldProducts(Long userId) throws InstanceNotFoundException {
        User user = permissionChecker.checkUser(userId);
        Set<Product> soldProducts = new HashSet<>();

        orderItemDao.findOrderItemByProductUserId(user.getId()).forEach(orderItem ->
                soldProducts.add(orderItem.getProduct())
        );

        return List.copyOf(soldProducts);
    }

    /**
     * Retrieves a specific product by its ID.
     *
     * @param productId the ID of the product to retrieve
     * @return the Product object
     * @throws InstanceNotFoundException if the product does not exist
     */
    @Override
    public Product getProductById(Long userId, Long productId) throws InstanceNotFoundException, PermissionException {
        return permissionChecker.checkProductExistsAndBelongsTo(productId, userId);

    }


    /**
     * Retrieves a category by its ID.
     *
     * @param categoryId the ID of the category to retrieve
     * @return the Category object
     * @throws InstanceNotFoundException if the category does not exist
     */
    private Category getCategoryById(Long categoryId) throws InstanceNotFoundException {
        return categoryDao.findById(categoryId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.category", categoryId));
    }

    /**
     * Updates the fields of the specified product.
     *
     * @param product the Product object to update
     * @param name the new name of the product
     * @param description the new full description of the product
     * @param shortDescription the new brief description of the product
     * @param price the new price of the product
     * @param discount the new discount on the product
     * @param stock the new available stock for the product
     * @param rating the new rating of the product
     * @param brand the new brand of the product
     * @param length the new length of the product
     * @param width the new width of the product
     * @param height the new height of the product
     * @param weight the new weight of the product
     * @param category the new Category object
     */
    private void updateProductFields(Product product, String name, String description, String shortDescription,
                                     BigDecimal price, BigDecimal discount, int stock, BigDecimal rating, Boolean isService,
                                     String brand, BigDecimal length, BigDecimal width, BigDecimal height,
                                     BigDecimal weight, Category category) {
        product.setName(name);
        product.setDescription(description);
        product.setShortDescription(shortDescription);
        product.setPrice(price);
        product.setDiscount(discount);
        product.setStock(stock);
        product.setRating(rating);
        product.setIsService(isService);
        product.setBrand(brand);
        product.setLength(length);
        product.setWidth(width);
        product.setHeight(height);
        product.setWeight(weight);
        product.setCategory(category);
    }

    /**
     * Clears all images associated with a product.
     *
     * @param product the Product object whose images will be cleared
     */
    @Transactional
    public void clearProductImages(Product product) {
        productImageDao.deleteAll(product.getImages());
        product.getImages().clear();
    }

    /**
     * Manages the images for a product, ensuring limits are adhered to.
     *
     * @param product the Product object for which images are being managed
     * @param images an array of image URLs to associate with the product
     * @throws MaxImagesExceededException if the number of images exceeds the allowed limit
     * @throws PrimaryImageNotFoundException if no primary image is provided
     */
    private void manageProductImages(Product product, String[] images)
            throws MaxImagesExceededException, PrimaryImageNotFoundException {

        if (images.length > MAX_IMAGES) {
            throw new MaxImagesExceededException(MAX_IMAGES);
        }

        boolean foundPrimaryImage = false;

        for (String image : images) {
            boolean isPrimary = image.endsWith("_main.jpg") || image.endsWith("_main.png");
            ProductImage productImage = new ProductImage(image, product, isPrimary);
            product.addImage(productImage);
            productImageDao.save(productImage);

            if (isPrimary) {
                foundPrimaryImage = true;
            }
        }

        if (!foundPrimaryImage) {
            throw new PrimaryImageNotFoundException();
        }
    }
}
