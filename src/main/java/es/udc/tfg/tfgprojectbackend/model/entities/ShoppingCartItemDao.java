package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShoppingCartItemDao  extends CrudRepository<ShoppingCartItem, Long>, PagingAndSortingRepository<ShoppingCartItem, Long> {

}
