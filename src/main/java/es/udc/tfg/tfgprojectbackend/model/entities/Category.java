package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.*;
import java.util.List;
/**
 * Represents a category in the application.
 * A category can have a parent category and can contain multiple subcategories.
 * If a parent category is deleted, all its subcategories will be deleted as well.
 */
@Entity
public class Category {

    /** The unique identifier for the category */
    private Long id;

    /** The name of the category */
    private String name;

    /** The parent category of this category, if applicable */
    private Category parentCategory;

    /** The list of subcategories belonging to this category */
    private List<Category> subcategories;

    /** Default constructor */
    public Category() {}

    /**
     * Constructor with parameters.
     *
     * @param name The name of the category
     */
    public Category(String name) {
        this.name = name;
    }

    /**
     * Gets the unique identifier for the category.
     *
     * @return the unique identifier of the category
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {

                                     return id;
    }
    

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the category.
     *
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the parent category of this category.
     *
     * @return the parent category, or null if this category has no parent
     */
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * Gets the list of subcategories belonging to this category.
     *
     * @return the list of subcategories
     */
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public List<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Category> subcategories) {
        this.subcategories = subcategories;
    }
}
