package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.Category;
import es.udc.tfg.tfgprojectbackend.model.entities.Product;
import es.udc.tfg.tfgprojectbackend.model.exceptions.CategoryHasProductsException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;

import java.util.List;

/**
 * Service interface for managing categories and products in the catalog.
 */
public interface CatalogService {

    /**
     * Retrieves all parent categories in the catalog.
     *
     * @return a list of parent categories
     */
    List<Category> findParentCategories();

    /**
     * Finds a category by its ID.
     *
     * @param id the ID of the category
     * @return the found category
     * @throws InstanceNotFoundException if the category does not exist
     */
    Category findCategoryById(Long id) throws InstanceNotFoundException;

    /**
     * Adds a new category to the catalog.
     *
     * @param name the name of the new category
     * @return an updated list of all categories after adding the new category
     * @throws DuplicateInstanceException if a category with the same name already exists
     */
    Category addCategory(String name) throws DuplicateInstanceException;


    /**
     * Updates the name of an existing category.
     *
     * @param id the ID of the category to update
     * @param name the new name for the category
     * @return the updated Category object
     * @throws InstanceNotFoundException if the category does not exist
     * @throws DuplicateInstanceException if a category with the new name already exists
     */
    Category updateCategory(Long id, String name) throws InstanceNotFoundException, DuplicateInstanceException;


    /**
     * Deletes a category from the catalog.
     *
     * @param id the ID of the category to delete
     * @return an updated list of all categories after deletion
     * @throws InstanceNotFoundException if the category does not exist
     * @throws CategoryHasProductsException if the category has associated products
     */
    List<Category> deleteCategory(Long id) throws InstanceNotFoundException, CategoryHasProductsException;

    /**
     * Adds a subcategory to a parent category.
     *
     * @param parentId the ID of the parent category
     * @param subcategoryName the name of the new subcategory
     * @return an updated list of all subcategories under the parent category
     * @throws InstanceNotFoundException if the parent category does not exist
     * @throws DuplicateInstanceException if a subcategory with the same name already exists under the parent category
     */
    Category addSubcategory(Long parentId, String subcategoryName) throws InstanceNotFoundException, DuplicateInstanceException;

    /**
     * Finds a product by its ID.
     *
     * @param id the ID of the product
     * @return the found product
     * @throws InstanceNotFoundException if the product does not exist
     */
    Product findProductById(Long id) throws InstanceNotFoundException;

    /**
     * Retrieves a block of products based on category and search keywords.
     *
     * @param categoryId the ID of the category to filter products
     * @param keywords   the search keywords for filtering products
     * @param rating     the minimum rating that the products must have
     * @param page       the page number for pagination
     * @param size       the number of products per page
     * @return a block of products matching the criteria
     */
    Block<Product> findProducts(Long categoryId, String keywords, Double rating, int page, int size);

    Block<Product> findAllProducts(Long categoryId, String keywords, Double rating, int page, int size);


}
