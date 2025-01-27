package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.Category;
import es.udc.tfg.tfgprojectbackend.model.entities.CategoryDao;
import es.udc.tfg.tfgprojectbackend.model.entities.Product;
import es.udc.tfg.tfgprojectbackend.model.entities.ProductDao;
import es.udc.tfg.tfgprojectbackend.model.exceptions.CategoryHasProductsException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Category> findParentCategories() {
        return categoryDao.findByParentCategoryNullOrderByName();
    }

    @Transactional(readOnly = true)
    @Override
    public Category findCategoryById(Long id) throws InstanceNotFoundException {
        return categoryDao.findById(id)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.category", id));
    }

    @Override
    public Category addCategory(String name) throws DuplicateInstanceException {
        if (categoryDao.findByName(name).isPresent()) {
            throw new DuplicateInstanceException("project.entities.category", name);
        }

        Category category = new Category(name);
        category.setSubcategories(new ArrayList<>());
        categoryDao.save(category);
        return category;
    }

    @Override
    public Category updateCategory(Long id, String name) throws InstanceNotFoundException, DuplicateInstanceException {

        Category category = categoryDao.findById(id)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.category", id));

        if (categoryDao.findByName(name).isPresent()) {
            throw new DuplicateInstanceException("project.entities.category", name);
        }

        category.setName(name);
        categoryDao.save(category);
        return category;

    }

    @Override
    public List<Category> deleteCategory(Long id) throws InstanceNotFoundException, CategoryHasProductsException {
        Category category = findCategoryById(id);

        if (productDao.existsByCategory(category)) {
            throw new CategoryHasProductsException(category.getName());
        }

        categoryDao.delete(category);
        return findParentCategories();
    }

    @Override
    public Category addSubcategory(Long parentId, String subcategoryName)
            throws InstanceNotFoundException, DuplicateInstanceException {

        Category parentCategory = findCategoryById(parentId);

        if (categoryDao.findByName(subcategoryName).isPresent()) {
            throw new DuplicateInstanceException("project.entities.category", subcategoryName);
        }

        Category subcategory = new Category(subcategoryName);

        subcategory.setParentCategory(parentCategory);
        parentCategory.getSubcategories().add(subcategory);

        categoryDao.save(subcategory);
        categoryDao.save(parentCategory);

        return parentCategory;
    }



    @Override
    public Product findProductById(Long id) throws InstanceNotFoundException {
        return productDao.findById(id)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", id));
    }

    @Override
    public Block<Product> findProducts(Long categoryId, String keywords, Double rating, int page, int size) {

        long totalProducts = productDao.count(categoryId, keywords, rating);
        Slice<Product> slice = productDao.find(categoryId, keywords, rating, page, size);

        return createBlock(slice, totalProducts);
    }

    @Override
    public Block<Product> findAllProducts(Long categoryId, String keywords, Double rating, int page, int size){

        long totalProducts = productDao.countAllProducts(categoryId, keywords, rating);
        Slice<Product> slice = productDao.findAllProducts(categoryId, keywords, rating, page, size);

        return createBlock(slice, totalProducts);
    }



    /**
     * Creates a Block of products from the given Slice and total product count.
     *
     * @param slice         the Slice of products
     * @param totalProducts the total number of products
     * @return a Block containing the product list and pagination info
     */
    private Block<Product> createBlock(Slice<Product> slice, long totalProducts) {
        return new Block<>(slice.getContent(), (int) totalProducts, slice.hasNext());
    }

}
