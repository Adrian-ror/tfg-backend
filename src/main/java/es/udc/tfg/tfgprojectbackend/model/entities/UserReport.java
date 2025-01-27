package es.udc.tfg.tfgprojectbackend.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserReport {

    public enum ReportType {
        PRODUCT,
        USER
    }

    public enum ReportStatus {
        PENDING,
        IN_PROGRESS,
        RESOLVED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "reportedProductId")
    private Product reportedProduct;

    @ManyToOne
    @JoinColumn(name = "reportedUserId")
    private User reportedUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "reportType", nullable = false)
    private ReportType reportType;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReportStatus status;

    @Column(name = "reportedAt", nullable = false)
    private LocalDateTime reportedAt;

    public UserReport() {
    }

    public UserReport(User user, Product reportedProduct, User reportedUser, ReportType reportType, String description, ReportStatus status, LocalDateTime reportedAt) {
        this.user = user;
        this.reportedProduct = reportedProduct;
        this.reportedUser = reportedUser;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getReportedProduct() {
        return reportedProduct;
    }

    public void setReportedProduct(Product reportedProduct) {
        this.reportedProduct = reportedProduct;
    }

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User reportedUser) {
        this.reportedUser = reportedUser;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public LocalDateTime getReportedAt() {
        return reportedAt;
    }

    public void setReportedAt(LocalDateTime reportedAt) {
        this.reportedAt = reportedAt;
    }


}
