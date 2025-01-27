package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageDao extends CrudRepository<Message, Long>, PagingAndSortingRepository<Message, Long> {
}
