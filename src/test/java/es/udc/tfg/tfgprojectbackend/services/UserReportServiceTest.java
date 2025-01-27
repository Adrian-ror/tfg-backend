package es.udc.tfg.tfgprojectbackend.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.DuplicateInstanceException;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;

import es.udc.tfg.tfgprojectbackend.model.services.UserReportService;
import es.udc.tfg.tfgprojectbackend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserReportServiceTest {

    @Autowired
    private UserReportService userReportService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    private User createUser(String userName, User.RoleType role, User.StatusType status) {
        return new User(userName, "password", "FirstName", "LastName",
                userName + "@example.com", "123456789", null, role, status);
    }

    private Product createProduct(User user, String productName) {
        Category newCategory = categoryDao.save(new Category("newCategory"));

        Product newProduct = new Product(
                productName,
                "This is a detailed description of the product.",
                "Short description of the product.",
                new BigDecimal("19.99"),
                new BigDecimal("0.10"),
                100,
                new BigDecimal("4.5"),
                true,
                false,
                "BrandName",
                new BigDecimal("10.0"),
                new BigDecimal("5.0"),
                new BigDecimal("2.0"),
                new BigDecimal("0.5"),
                LocalDateTime.now(),
                newCategory,
                user
        );
        return productDao.save(newProduct);
    }

    @Test
    public void testCreateReport() throws InstanceNotFoundException, DuplicateInstanceException {
        User reporter = createUser("reporter", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        User reportedUser = createUser("reportedUser", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(reporter);
        userService.signUp(reportedUser);

        UserReport report = userReportService.createReport(
                reporter.getId(),
                null,
                reportedUser.getId(),
                UserReport.ReportType.USER,
                "User violated rules");

        assertNotNull(report.getId());
        assertEquals(reporter.getId(), report.getUser().getId());
        assertEquals(reportedUser.getId(), report.getReportedUser().getId());
        assertEquals(UserReport.ReportType.USER, report.getReportType());
        assertEquals("User violated rules", report.getDescription());
        assertEquals(UserReport.ReportStatus.PENDING, report.getStatus());
    }

    @Test
    public void testCreateProductReport() throws InstanceNotFoundException, DuplicateInstanceException {
        User reporter = createUser("reporter", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(reporter);
        userService.signUp(provider);

        Product reportedProduct = createProduct(provider, "Reported Product");

        UserReport productReport = userReportService.createReport(
                reporter.getId(),
                reportedProduct.getId(),
                null,
                UserReport.ReportType.PRODUCT,
                "Product is defective");

        assertNotNull(productReport.getId());
        assertEquals(reporter.getId(), productReport.getUser().getId());
        assertEquals(reportedProduct.getId(), productReport.getReportedProduct().getId());
        assertEquals(UserReport.ReportType.PRODUCT, productReport.getReportType());
        assertEquals("Product is defective", productReport.getDescription());
        assertEquals(UserReport.ReportStatus.PENDING, productReport.getStatus());
    }

    @Test
    public void testGetProductReport() throws InstanceNotFoundException, DuplicateInstanceException {
        User reporter = createUser("reporter", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        User provider = createUser("provider", User.RoleType.PROVIDER, User.StatusType.ACTIVE);
        userService.signUp(reporter);
        userService.signUp(provider);

        Product reportedProduct = createProduct(provider, "Reported Product");

        UserReport createdProductReport = userReportService.createReport(
                reporter.getId(),
                reportedProduct.getId(),
                null,
                UserReport.ReportType.PRODUCT,
                "Product is defective");

        UserReport fetchedProductReport = userReportService.getReport(createdProductReport.getId());
        assertEquals(createdProductReport, fetchedProductReport);
    }



    @Test
    public void testGetReport() throws InstanceNotFoundException, DuplicateInstanceException {
        User reporter = createUser("reporter", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        User reportedUser = createUser("reportedUser", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(reporter);
        userService.signUp(reportedUser);

        UserReport createdReport = userReportService.createReport(
                reporter.getId(),
                null,
                reportedUser.getId(),
                UserReport.ReportType.USER,
                "User violated rules");

        UserReport fetchedReport = userReportService.getReport(createdReport.getId());
        assertEquals(createdReport, fetchedReport);
    }

    @Test
    public void testGetReportWithNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> userReportService.getReport(-1L));
    }

    @Test
    public void testGetAllReports() throws InstanceNotFoundException, DuplicateInstanceException {
        User reporter = createUser("reporter", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        User reportedUser = createUser("reportedUser", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(reporter);
        userService.signUp(reportedUser);

        userReportService.createReport(reporter.getId(), null, reportedUser.getId(),
                UserReport.ReportType.USER, "First report");
        userReportService.createReport(reporter.getId(), null, reportedUser.getId(),
                UserReport.ReportType.USER, "Second report");

        List<UserReport> reports = userReportService.getAllReports();
        assertEquals(2, reports.size());
    }

    @Test
    public void testUpdateReportStatus() throws InstanceNotFoundException, DuplicateInstanceException {
        User reporter = createUser("reporter", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        User reportedUser = createUser("reportedUser", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(reporter);
        userService.signUp(reportedUser);

        UserReport report = userReportService.createReport(reporter.getId(), null, reportedUser.getId(),
                UserReport.ReportType.USER, "Update status test");

        userReportService.updateReportStatus(report.getId(), UserReport.ReportStatus.RESOLVED);
        UserReport updatedReport = userReportService.getReport(report.getId());
        assertEquals(UserReport.ReportStatus.RESOLVED, updatedReport.getStatus());
    }

    @Test
    public void testUpdateReportStatusWithNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () ->
                userReportService.updateReportStatus(-1L, UserReport.ReportStatus.RESOLVED));
    }

    @Test
    public void testGetReportsByStatus() throws InstanceNotFoundException, DuplicateInstanceException {
        User reporter = createUser("reporter", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        User reportedUser = createUser("reportedUser", User.RoleType.CLIENT, User.StatusType.ACTIVE);
        userService.signUp(reporter);
        userService.signUp(reportedUser);

        UserReport report1 = userReportService.createReport(reporter.getId(), null, reportedUser.getId(),
                UserReport.ReportType.USER, "Report 1");
        UserReport report2 = userReportService.createReport(reporter.getId(), null, reportedUser.getId(),
                UserReport.ReportType.USER, "Report 2");

        userReportService.updateReportStatus(report2.getId(), UserReport.ReportStatus.RESOLVED);

        List<UserReport> pendingReports = userReportService.getReportsByStatus(UserReport.ReportStatus.PENDING);
        List<UserReport> resolvedReports = userReportService.getReportsByStatus(UserReport.ReportStatus.RESOLVED);

        assertEquals(1, pendingReports.size());
        assertTrue(pendingReports.contains(report1));

        assertEquals(1, resolvedReports.size());
        assertTrue(resolvedReports.contains(report2));
    }
}
