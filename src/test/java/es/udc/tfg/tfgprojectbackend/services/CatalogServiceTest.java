package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.CategoryHasProductsException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.services.Block;
import es.udc.tfg.tfgprojectbackend.model.services.CatalogService;
import es.udc.tfg.tfgprojectbackend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {

    private final Long NON_EXISTENT_ID = -1L;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    private User createUser(String userName, User.RoleType role, User.StatusType status) {
        return new User(userName, "password", "FirstName", "LastName",
                userName + "@example.com", "123456789", null, role, status);
    }

    private Product createProduct(User user, String productName) {
        Category newCategory = categoryDao.save(new Category("Electronics"));

        Product newProduct = new Product(
                productName,
                "This is a detailed description of the product.",
                "Short description of the product.",
                new BigDecimal("19.99"),
                new BigDecimal("0.10"),
                100,
                new BigDecimal("0"),
                true,
                false,
                "BrandName",
                new BigDecimal("10.0"),
                new BigDecimal("5.0"),
                new BigDecimal("2.0"),
                new BigDecimal("0.5"),
                LocalDateTime.now(),
                newCategory,
                user
        );
        return productDao.save(newProduct);
    }


    @Test
    public void testAddCategory() throws DuplicateInstanceException {
        Category category = catalogService.addCategory("Electronics");

        assertNotNull(category);
        assertEquals("Electronics", category.getName());
    }

    @Test
    public void testAddDuplicateCategory() throws DuplicateInstanceException {
        catalogService.addCategory("Electronics");

        assertThrows(DuplicateInstanceException.class, () -> catalogService.addCategory("Electronics"));
    }

    @Test
    public void testFindCategoryById() throws DuplicateInstanceException, InstanceNotFoundException {
        Category category = catalogService.addCategory("Electronics");
        Category foundCategory = catalogService.findCategoryById(category.getId());

        assertEquals(category, foundCategory);
    }

    @Test
    public void testFindCategoryByNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> catalogService.findCategoryById(NON_EXISTENT_ID));
    }

    @Test
    public void testUpdateCategory() throws DuplicateInstanceException, InstanceNotFoundException {
        Category category = catalogService.addCategory("Electronics");
        Category updatedCategory = catalogService.updateCategory(category.getId(), "Home Appliances");

        assertEquals("Home Appliances", updatedCategory.getName());
    }

    @Test
    public void testUpdateCategoryWithDuplicateName() throws DuplicateInstanceException, InstanceNotFoundException {
        Category category = catalogService.addCategory("Electronics");
        catalogService.addCategory("Home Appliances");

        assertThrows(DuplicateInstanceException.class, () -> catalogService.updateCategory(category.getId(), "Home Appliances"));
    }

    @Test
    public void testDeleteCategory() throws DuplicateInstanceException, InstanceNotFoundException, CategoryHasProductsException {
        Category category = catalogService.addCategory("Electronics");
        catalogService.addCategory("Home Appliances");

        List<Category> categories = catalogService.deleteCategory(category.getId());

        assertFalse(categories.contains(category));
    }

    @Test
    public void testDeleteCategoryWithProducts() throws DuplicateInstanceException, InstanceNotFoundException {
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);
        Product product = createProduct(provider, "Smartphone");

        assertThrows(CategoryHasProductsException.class, () -> catalogService.deleteCategory(product.getCategory().getId()));
    }

    @Test
    public void testAddSubcategory() throws DuplicateInstanceException, InstanceNotFoundException {
        Category parentCategory = catalogService.addCategory("Electronics");

        Category subcategory = catalogService.addSubcategory(parentCategory.getId(), "Mobile Phones");

        boolean subcategoryExists = parentCategory.getSubcategories().stream()
                .anyMatch(subcat -> subcat.getName().equals("Mobile Phones"));

        assertTrue(subcategoryExists);

        assertEquals(parentCategory, subcategory.getSubcategories().getLast().getParentCategory());
    }


    @Test
    public void testAddSubcategoryWithDuplicateName() throws DuplicateInstanceException, InstanceNotFoundException {
        Category parentCategory = catalogService.addCategory("Electronics");
        catalogService.addSubcategory(parentCategory.getId(), "Mobile Phones");

        assertThrows(DuplicateInstanceException.class, () -> catalogService.addSubcategory(parentCategory.getId(), "Mobile Phones"));
    }

    @Test
    public void testFindProductById() throws InstanceNotFoundException, DuplicateInstanceException {

        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);
        Product product = createProduct(provider, "Smartphone");

        Product foundProduct = catalogService.findProductById(product.getId());

        assertEquals(product, foundProduct);
    }

    @Test
    public void testFindProductByNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> catalogService.findProductById(NON_EXISTENT_ID));
    }

    @Test
    public void testFindProducts() throws DuplicateInstanceException {

        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);
        Product product = createProduct(provider, "Smartphone");
        Block<Product> productsBlock = catalogService.findProducts(product.getCategory().getId(), "smart", 0.0, 0, 6);

        assertNotNull(productsBlock);
        assertFalse(productsBlock.getItems().isEmpty());
    }

    @Test
    public void testFindAllProducts() throws DuplicateInstanceException {

        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(provider);
        Product product = createProduct(provider, "Smartphone");

        Block<Product> productsBlock = catalogService.findAllProducts(product.getCategory().getId(), "phone", 0.0, 0, 10);

        assertNotNull(productsBlock);
        assertFalse(productsBlock.getItems().isEmpty());
    }
}
