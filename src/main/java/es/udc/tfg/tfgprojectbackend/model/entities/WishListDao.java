package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WishListDao extends CrudRepository<WishList, Long>, PagingAndSortingRepository<WishList, Long> {

}
