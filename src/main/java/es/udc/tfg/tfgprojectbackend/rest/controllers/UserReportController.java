package es.udc.tfg.tfgprojectbackend.rest.controllers;

import es.udc.tfg.tfgprojectbackend.model.entities.UserReport;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import es.udc.tfg.tfgprojectbackend.model.services.UserReportService;
import es.udc.tfg.tfgprojectbackend.rest.dtos.UserReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static es.udc.tfg.tfgprojectbackend.rest.dtos.UserReportConversor.toUserReportDto;
import static es.udc.tfg.tfgprojectbackend.rest.dtos.UserReportConversor.toUserReportDtos;

@RestController
@RequestMapping("/reports")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    /**
     * Creates a new user report.
     */
    @PostMapping
    public UserReportDto createReport(@RequestAttribute Long userId,
                                                       @RequestBody UserReportDto reportDto)
            throws InstanceNotFoundException {

        UserReport report = userReportService.createReport(
                userId,
                reportDto.getReportedProductId(),
                reportDto.getReportedUserId(),
                reportDto.getReportType(),
                reportDto.getDescription()
        );
        return toUserReportDto(report);
    }

    /**
     * Retrieves a user report by ID.
     */
    @GetMapping("/{reportId}")
    public UserReportDto getReport(@PathVariable Long reportId) throws InstanceNotFoundException {
        UserReport report = userReportService.getReport(reportId);
        return toUserReportDto(report);
    }

    /**
     * Retrieves all user reports.
     */
    @GetMapping
    public List<UserReportDto> getAllReports() {
        return toUserReportDtos(userReportService.getAllReports());
    }

    /**
     * Updates the status of a user report.
     */
    @PatchMapping("/{reportId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateReportStatus(@PathVariable Long reportId,
                                                   @RequestParam UserReport.ReportStatus newStatus)
            throws InstanceNotFoundException {

        userReportService.updateReportStatus(reportId, newStatus);
    }

    /**
     * Retrieves user reports by status.
     */
    @GetMapping("/status")
    public List<UserReportDto> getReportsByStatus(@RequestParam UserReport.ReportStatus status) {
        return toUserReportDtos(userReportService.getReportsByStatus(status));
    }
}
