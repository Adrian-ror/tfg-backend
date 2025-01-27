package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductDao extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long>, CustomizedProductDao {

    /**
     * Checks if there are any products associated with a specific category.
     *
     * @param category the category to check for products
     * @return true if products exist in the category, false otherwise
     */
    boolean existsByCategory(Category category);

    /**
     * Finds a product by the user and product IDs.
     *
     * @param userId the ID of the user
     * @param id the ID of the product
     * @return an Optional containing the product if found, otherwise empty
     */
    Optional<Product> findByUserIdAndId(Long userId, Long id);

    /**
     * Finds all products associated with a specific user.
     *
     * @param user the user whose products are to be retrieved
     * @return a list of products associated with the user
     */
    List<Product> findAllByUser(User user);
}
