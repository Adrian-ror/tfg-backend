package es.udc.tfg.tfgprojectbackend.model.entities;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Repository interface for accessing and managing UserReport entities.
 * Extends CrudRepository and PagingAndSortingRepository for CRUD and pagination operations.
 */
public interface UserReportDao extends CrudRepository<UserReport, Long>, PagingAndSortingRepository<UserReport, Long> {

    /**
     * Finds all user reports with a specific status.
     *
     * @param status the status of the user reports to be retrieved.
     * @return a list of user reports with the given status.
     */
    List<UserReport> findByStatus(UserReport.ReportStatus status);

}
