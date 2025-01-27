package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShippingMethodDao extends CrudRepository<ShippingMethod, Long>, PagingAndSortingRepository<ShippingMethod, Long> {
}
