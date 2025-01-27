package es.udc.tfg.tfgprojectbackend.model.entities;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WishListItemDao extends CrudRepository<WishListItem, Long>, PagingAndSortingRepository<WishListItem, Long> {

}