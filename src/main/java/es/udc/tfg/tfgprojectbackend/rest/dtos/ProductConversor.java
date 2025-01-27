package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.Product;
import es.udc.tfg.tfgprojectbackend.model.entities.ProductImage;
import es.udc.tfg.tfgprojectbackend.model.entities.ProductReview;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConversor {

    public final static ProductDto toProductDto(Product product) {

        String[] images = product.getImages().stream()
                .map(ProductImage::getImageUrl)
                .toArray(String[]::new);

        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getShortDescription(),
                product.getPrice(),
                product.getDiscount(),
                product.getStock(),
                product.getRating(),
                product.getReviews().size(),
                product.getIsVisible(),
                product.getBrand(),
                product.getIsService(),
                product.getLength(),
                product.getWidth(),
                product.getHeight(),
                product.getWeight(),
                toMillis(product.getCreatedAt()),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getUser().getId(),
                product.getUser().getUserName(),
                images
        );
    }

    public final static List<ProductDto> toProductDtos(List<Product> products) {
        return products.stream()
                .map(ProductConversor::toProductDto)
                .collect(Collectors.toList());
    }


    public final static List<ProductSummaryDto> toProductSummaryDtos(List<Product> products) {
        return products.stream()
                .map(ProductConversor::toProductSummaryDto)
                .collect(Collectors.toList());
    }

    public final static ProductSummaryDto toProductSummaryDto(Product product) {

        String mainImage = product.getImages().stream()
                .filter(ProductImage::getIsPrimary)
                .map(ProductImage::getImageUrl)
                .findFirst()
                .orElse(null);

        return new ProductSummaryDto(
                product.getId(),
                product.getName(),
                mainImage,
                product.getPrice(),
                product.getCategory().getName(),
                product.getShortDescription(),
                product.getRating(),
                product.getIsVisible(),
                product.getIsService()
        );
    }

    public static ProductReviewDto toProductReviewDto(ProductReview productReview) {
        return new ProductReviewDto(
                productReview.getId(),
                productReview.getProduct().getId(),
                productReview.getUser().getId(),
                productReview.getUser().getUserName(),
                productReview.getRating(),
                productReview.getComment(),
                toMillis(productReview.getReviewDate())
        );
    }

    public static List<ProductReviewDto> toProductReviewDtos(List<ProductReview> productReviews) {
        return productReviews.stream()
                .map(ProductConversor::toProductReviewDto)
                .collect(Collectors.toList());
    }

    private final static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }

}
