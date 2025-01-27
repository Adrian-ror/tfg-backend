package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.Category;
import es.udc.tfg.tfgprojectbackend.model.entities.Product;
import es.udc.tfg.tfgprojectbackend.model.exceptions.CategoryHasProductsException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.services.Block;
import es.udc.tfg.tfgprojectbackend.model.services.CatalogService;
import es.udc.tfg.tfgprojectbackend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import es.udc.tfg.tfgprojectbackend.rest.common.ErrorsDto;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.CategoryConversor.toCategoryDto;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.ProductConversor.toProductDto;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.ProductConversor.toProductSummaryDtos;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final static String CATEGORY_HAS_PRODUCTS_CODE = "project.exceptions.CategoryHasProductsException";

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private MessageSource messageSource;


    @ExceptionHandler(CategoryHasProductsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleCategoryHasProductsException(CategoryHasProductsException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(CATEGORY_HAS_PRODUCTS_CODE, new Object[]{exception.getCategoryName()},
                CATEGORY_HAS_PRODUCTS_CODE, locale);
        return new ErrorsDto(errorMessage);
    }





    @GetMapping("/parent_categories")
    public List<CategoryDto> findParentCategories() {
        return toCategoryDtos(catalogService.findParentCategories());
    }

    @GetMapping("/categories/{id}")
    public CategoryDto findCategoryById(@PathVariable Long id) throws InstanceNotFoundException {
        Category category = catalogService.findCategoryById(id);
        return toCategoryDto(category, new HashSet<>());
    }

    @PostMapping("/categories/new")
    public CategoryDto addCategory(@RequestBody CategoryParamsDto params) throws DuplicateInstanceException {
        Category category = catalogService.addCategory(params.getName());
        return toCategoryDto(category, new HashSet<>());
    }

    @PutMapping("/categories/{id}/update")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryParamsDto params)
            throws InstanceNotFoundException, DuplicateInstanceException {
        Category category = catalogService.updateCategory(id, params.getName());
        return toCategoryDto(category, new HashSet<>());
    }

    @DeleteMapping("/categories/{id}/delete")
    public List<CategoryDto> deleteCategory(@PathVariable Long id) throws InstanceNotFoundException, CategoryHasProductsException {
        List<Category> categories = catalogService.deleteCategory(id);
        return toCategoryDtos(categories);
    }

    @PostMapping("/categories/{parentId}/subcategories")
    public CategoryDto addSubcategory(@PathVariable Long parentId, @RequestBody CategoryParamsDto params)
            throws InstanceNotFoundException, DuplicateInstanceException {
        Category category = catalogService.addSubcategory(parentId, params.getName());
        return toCategoryDto(category, new HashSet<>());
    }

    @GetMapping("/products/{id}")
    public ProductDto findProductById(@PathVariable Long id) throws InstanceNotFoundException {
        return toProductDto(catalogService.findProductById(id));
    }

    @GetMapping("/products")
    public BlockDto<ProductSummaryDto> findProducts(
            @RequestParam(required=false) Long categoryId,
            @RequestParam(required=false) String keywords,
            @RequestParam(required=false) Double rating,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="6") int size) {

        Block<Product> productBlock = catalogService.findProducts(categoryId,
                keywords != null ? keywords.trim() : null, rating, page, size);

        return new BlockDto<>(toProductSummaryDtos(productBlock.getItems()),
                productBlock.getTotalItems(), productBlock.existsMoreItems());
    }

    @GetMapping("/all-products")
    public BlockDto<ProductSummaryDto> findAllProducts(
            @RequestParam(required=false) Long categoryId,
            @RequestParam(required=false) String keywords,
            @RequestParam(required=false) Double rating,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="6") int size) {

        Block<Product> productBlock = catalogService.findAllProducts(
                categoryId,
                keywords != null ? keywords.trim() : null,
                rating,
                page,
                size
        );

        return new BlockDto<>(toProductSummaryDtos(productBlock.getItems()),
                productBlock.getTotalItems(),
                productBlock.existsMoreItems());
    }


}
