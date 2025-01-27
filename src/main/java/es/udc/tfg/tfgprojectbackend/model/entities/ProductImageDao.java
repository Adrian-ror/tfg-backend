package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductImageDao extends CrudRepository<ProductImage, Long>, PagingAndSortingRepository<ProductImage, Long> {
}

