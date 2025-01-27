package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.domain.Slice;

/**
 * Interface for customized product data access methods.
 */
public interface CustomizedProductDao {

    /**
     * Finds a slice of products based on the provided filters.
     *
     * @param categoryId the ID of the category to filter products
     * @param text       the text to search for in product names
     * @param minRating  the minimum rating that the products must have
     * @param page       the page number for pagination
     * @param size       the page size
     * @return a Slice of products that match the search criteria
     */
    Slice<Product> find(Long categoryId, String text, Double minRating, int page, int size);

    /**
     * Counts the total number of products that meet the specified criteria.
     *
     * @param categoryId the ID of the category to filter products
     * @param keywords   the keywords to search for in product names
     * @param minRating  the minimum rating that the products must have
     * @return the total number of products that match the criteria
     */
    long countAllProducts(Long categoryId, String keywords, Double minRating);

    /**
     * Finds all products that match the specified criteria.
     *
     * @param categoryId the ID of the category to filter products
     * @param keywords   the keywords to search for in product names
     * @param minRating  the minimum rating that the products must have
     * @param page       the page number for pagination
     * @param size       the page size
     * @return a Slice of products that match the search criteria
     */
    Slice<Product> findAllProducts(Long categoryId, String keywords, Double minRating, int page, int size);

    /**
     * Counts the total number of products that meet the specified criteria.
     *
     * @param categoryId the ID of the category to filter products
     * @param keywords   the keywords to search for in product names
     * @param minRating  the minimum rating that the products must have
     * @return the total number of products that match the criteria
     */
    long count(Long categoryId, String keywords, Double minRating);
}
