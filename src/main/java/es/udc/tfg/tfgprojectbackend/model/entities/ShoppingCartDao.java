package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShoppingCartDao  extends CrudRepository<ShoppingCart, Long>, PagingAndSortingRepository<ShoppingCart, Long> {

}
