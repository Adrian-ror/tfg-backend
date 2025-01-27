package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.UserReport;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;

import java.util.List;

/**
 * Service interface for managing user reports.
 */
public interface UserReportService {

    /**
     * Creates a new user report.
     *
     * @param userId the ID of the user creating the report
     * @param reportedProductId the ID of the product being reported (can be null if the report is not related to a product)
     * @param reportedUserId the ID of the user being reported
     * @param reportType the type of the report (product or service)
     * @param description the description of the issue
     * @return the created UserReport entity
     * @throws InstanceNotFoundException if the user, product, or service is not found
     */
    UserReport createReport(Long userId, Long reportedProductId, Long reportedUserId, UserReport.ReportType reportType,
                            String description) throws InstanceNotFoundException;

    /**
     * Retrieves a user report by its ID.
     *
     * @param reportId the ID of the report
     * @return the UserReport entity
     * @throws InstanceNotFoundException if the report is not found
     */
    UserReport getReport(Long reportId) throws InstanceNotFoundException;

    /**
     * Retrieves all user reports.
     *
     * @return a list of all UserReport entities
     */
    List<UserReport> getAllReports();

    /**
     * Updates the status of a user report.
     *
     * @param reportId the ID of the report to update
     * @param newStatus the new status to set for the report
     * @throws InstanceNotFoundException if the report is not found
     */
    void updateReportStatus(Long reportId, UserReport.ReportStatus newStatus) throws InstanceNotFoundException;

    /**
     * Retrieves user reports filtered by their status.
     *
     * @param status the status to filter reports by
     * @return a list of UserReport entities with the specified status
     */
    List<UserReport> getReportsByStatus(UserReport.ReportStatus status);
}
