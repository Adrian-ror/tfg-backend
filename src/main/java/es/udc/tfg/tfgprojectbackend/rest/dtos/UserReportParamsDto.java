package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.UserReport;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UserReportParamsDto {

    @NotNull
    private Long productId;

    @NotNull
    private UserReport.ReportType reportType;

    @NotNull
    @Size(max = 500)
    private String description;

    public UserReportParamsDto() {
    }

    public UserReportParamsDto(Long productId, UserReport.ReportType reportType, String description) {
        this.productId = productId;
        this.reportType = reportType;
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
}
