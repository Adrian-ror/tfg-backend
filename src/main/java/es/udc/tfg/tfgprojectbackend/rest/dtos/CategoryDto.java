package es.udc.tfg.tfgprojectbackend.rest.dtos;

import java.util.List;

/**
 * Data Transfer Object for Category.
 */
public class CategoryDto {

    private Long id;
    private String name;
    private CategoryDto parentCategory;
    private List<CategoryDto> subcategories;

    /** Default constructor */
    public CategoryDto() {}

    /**
     * Constructor with parameters.
     *
     * @param id The unique identifier of the category
     * @param name The name of the category
     */
    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor with parameters including parent category and subcategories.
     *
     * @param id The unique identifier of the category
     * @param name The name of the category
     * @param parentCategory The parent category DTO
     * @param subcategories The list of subcategory DTOs
     */
    public CategoryDto(Long id, String name, CategoryDto parentCategory, List<CategoryDto> subcategories) {
        this.id = id;
        this.name = name;
        this.parentCategory = parentCategory;
        this.subcategories = subcategories;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryDto getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(CategoryDto parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<CategoryDto> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<CategoryDto> subcategories) {
        this.subcategories = subcategories;
    }
}
