package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductReviewDao extends CrudRepository<ProductReview, Long>, PagingAndSortingRepository<ProductReview, Long> {

    Boolean existsProductReviewByUserIdAndProductId(Long userId, Long productId);
    List<ProductReview> findProductReviewByProductId(Long productId);
}
