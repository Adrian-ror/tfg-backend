package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.UserReport;

public class UserReportDto {

    private Long id;
    private Long reportedProductId;
    private Long reportedUserId;
    private UserReport.ReportType reportType;
    private String description;
    private UserReport.ReportStatus status;
    private long reportedAt;


    public UserReportDto() {
    }

    public UserReportDto(Long id, Long reportedProductId, Long reportedUserId, UserReport.ReportType reportType,
                         String description, UserReport.ReportStatus status, long reportedAt) {
        this.id = id;
        this.reportedProductId = reportedProductId;
        this.reportedUserId = reportedUserId;
        this.reportType = reportType;
        this.description = description;
        this.status = status;
        this.reportedAt = reportedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReportedProductId() {
        return reportedProductId;
    }

    public void setReportedProductId(Long reportedProductId) {
        this.reportedProductId = reportedProductId;
    }

    public Long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(Long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public UserReport.ReportType getReportType() {
        return reportType;
    }

    public void setReportType(UserReport.ReportType reportType) {
        this.reportType = reportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserReport.ReportStatus getStatus() {
        return status;
    }

    public void setStatus(UserReport.ReportStatus status) {
        this.status = status;
    }

    public long getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(long reportedAt) {
        this.reportedAt = reportedAt;
    }
}
