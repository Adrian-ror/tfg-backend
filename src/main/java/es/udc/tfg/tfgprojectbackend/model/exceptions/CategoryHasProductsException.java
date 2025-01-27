package es.udc.tfg.tfgprojectbackend.model.exceptions;

/**
 * Exception thrown when an attempt is made to delete a category
 * that still has associated products.
 */
@SuppressWarnings("serial")
public class CategoryHasProductsException extends Exception {

    private String categoryName;

    public CategoryHasProductsException(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getCategoryName() {
        return categoryName;
    }


}
