package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for managing Category entities.
 * Extends CrudRepository and PagingAndSortingRepository to provide basic CRUD operations
 * and pagination capabilities for Category entities.
 */
public interface CategoryDao extends CrudRepository<Category, Long>, PagingAndSortingRepository<Category, Long> {

    /**
     * Finds a category by its name.
     *
     * @param categoryName the name of the category to find
     * @return an Optional containing the category if found, or empty if not
     */
    Optional<Category> findByName(String categoryName);

    /**
     * Finds all top-level categories (without parent categories) and orders them by name.
     *
     * @return a list of top-level categories, ordered by name
     */
    List<Category> findByParentCategoryNullOrderByName();
}
