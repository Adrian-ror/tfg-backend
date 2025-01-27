package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.Product;
import es.udc.tfg.tfgprojectbackend.model.exceptions.*;
import es.udc.tfg.tfgprojectbackend.model.services.SellerService;
import es.udc.tfg.tfgprojectbackend.rest.common.ErrorsDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.PostParamsDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.ProductDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.ProductSummaryDto;
import es.udc.tfg.tfgprojectbackend.rest.dtos.UpdateParamsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.ProductConversor.*;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;


    @Autowired
    private MessageSource messageSource;

    private final static String MAX_IMAGES_EXCEEDED_EXCEPTION_CODE = "project.exceptions.MaxImagesExceededException";
    private final static String PRIMARY_IMAGE_NOT_FOUND_EXCEPTION_CODE = "project.exceptions.PrimaryImageNotFoundException";


    @ExceptionHandler(MaxImagesExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleMaxImagesExceededException(MaxImagesExceededException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(MAX_IMAGES_EXCEEDED_EXCEPTION_CODE, new Object[]{exception.getMaxAllowedImages()},
                MAX_IMAGES_EXCEEDED_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(PrimaryImageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handlePrimaryImageNotFoundException(PrimaryImageNotFoundException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(PRIMARY_IMAGE_NOT_FOUND_EXCEPTION_CODE, null,
                PRIMARY_IMAGE_NOT_FOUND_EXCEPTION_CODE, locale);
        return new ErrorsDto(errorMessage);
    }






    /**
     * Posts a new product for a user.
     *
     * @param userId     the ID of the user posting the product.
     * @param productDto the ProductDto object containing the product details.
     * @return the created Product.
     */
    @PostMapping("/products")
    public ProductDto postProduct(@RequestAttribute Long userId, @RequestBody PostParamsDto productDto)
            throws InstanceNotFoundException, MaxImagesExceededException, PrimaryImageNotFoundException {

        Product product = sellerService.post(userId,
                productDto.getTitle(), productDto.getDescription(), productDto.getShortDescription(),
                productDto.getPrice(), productDto.getDiscount(), productDto.getStock(), BigDecimal.ZERO, productDto.getIsService(),
                productDto.getBrand(), productDto.getLength(), productDto.getWidth(), productDto.getHeight(),
                productDto.getWeight(), productDto.getCategoryId(), productDto.getImages());
        return toProductDto(product);
    }

    /**
     * Updates an existing product for a user.
     *
     * @param userId     the ID of the user updating the product.
     * @param productId  the ID of the product to update.
     * @param productDto the ProductDto object containing the updated product details.
     * @return the updated Product.
     */
    @PutMapping("/products/{productId}")
    public ProductDto updateProduct(@RequestAttribute Long userId, @PathVariable Long productId,
                                    @RequestBody UpdateParamsDto productDto)
            throws InstanceNotFoundException, MaxImagesExceededException, PrimaryImageNotFoundException, PermissionException {

        Product updatedProduct = sellerService.update(userId, productId,
                productDto.getTitle(), productDto.getDescription(), productDto.getShortDescription(),
                productDto.getPrice(), productDto.getDiscount(), productDto.getStock(), BigDecimal.ZERO, productDto.getService(),
                productDto.getBrand(), productDto.getLength(), productDto.getWidth(), productDto.getHeight(),
                productDto.getWeight(), productDto.getCategoryId(), productDto.getImages());
        return toProductDto(updatedProduct);
    }

    /**
     * Deletes a product for a user.
     *
     * @param userId    the ID of the user deleting the product.
     * @param productId the ID of the product to delete.
     */
    @DeleteMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@RequestAttribute Long userId, @PathVariable Long productId)
            throws InstanceNotFoundException, PermissionException {

        sellerService.delete(userId, productId);
    }

    /**
     * Retrieves all products posted by a user.
     *
     * @param userId the ID of the user.
     * @return a list of products posted by the user.
     */
    @GetMapping("/products")
    public List<ProductSummaryDto> getAllProducts(@RequestAttribute  Long userId) throws InstanceNotFoundException {
        return toProductSummaryDtos(sellerService.getAllProducts(userId));
    }

    /**
     * Retrieves all products sold by a user.
     *
     * @param userId the ID of the user.
     * @return a list of products sold by the user.
     */
    @GetMapping("/sold-products")
    public List<ProductSummaryDto> getAllSoldProducts(@RequestAttribute Long userId) throws InstanceNotFoundException {
        return toProductSummaryDtos(sellerService.getAllSoldProducts(userId));

    }


    /**
     * Retrieves a specific product by its ID.
     *
     * @param userId the ID of the user requesting the product.
     * @param productId the ID of the product to retrieve.
     * @return the ProductDto object representing the product.
     * @throws InstanceNotFoundException if the product does not exist.
     * @throws PermissionException if the user does not have permission to view the product.
     */
    @GetMapping("/products/{productId}")
    public ProductDto getProductById(@RequestAttribute Long userId, @PathVariable Long productId)
            throws InstanceNotFoundException, PermissionException {

        Product product = sellerService.getProductById(userId, productId);
        return toProductDto(product);
    }

}
