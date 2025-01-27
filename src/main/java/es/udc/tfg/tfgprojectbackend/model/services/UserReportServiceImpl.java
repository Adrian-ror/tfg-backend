package es.udc.tfg.tfgprojectbackend.model.services;

import es.udc.tfg.tfgprojectbackend.model.entities.*;
import es.udc.tfg.tfgprojectbackend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserReportServiceImpl implements UserReportService {

    @Autowired
    private UserReportDao userReportDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private ProductDao productDao;

    @Override
    public UserReport createReport(Long userId, Long reportedProductId, Long reportedUserId, UserReport.ReportType reportType,
                                   String description) throws InstanceNotFoundException {


        User user = permissionChecker.checkUser(userId);

        Product reportedProduct = null;
        User reportedUser = null;

        if(reportedProductId != null){
             reportedProduct = productDao.findById(reportedProductId)
                    .orElseThrow(() -> new InstanceNotFoundException("project.entities.product", reportedProductId));
        }

        if(reportedUserId != null){
             reportedUser = userDao.findById(reportedUserId)
                    .orElseThrow(() -> new InstanceNotFoundException("project.entities.user", reportedUserId));
        }


        if( reportedProduct != null && !reportedProduct.getIsVisible()){
            throw new InstanceNotFoundException("project.entities.product", reportedProductId);
        }

        UserReport userReport = new UserReport(user, reportedProduct, reportedUser, reportType, description, UserReport.ReportStatus.PENDING ,LocalDateTime.now());

        return userReportDao.save(userReport);
    }


    @Override
    public UserReport getReport(Long reportId) throws InstanceNotFoundException {
        return userReportDao.findById(reportId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.userReport", reportId));
    }

    @Override
    public List<UserReport> getAllReports() {
        return StreamSupport.stream(userReportDao.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void updateReportStatus(Long reportId, UserReport.ReportStatus newStatus) throws InstanceNotFoundException {

        UserReport report = userReportDao.findById(reportId)
                .orElseThrow(() -> new InstanceNotFoundException("project.entities.userReport ", reportId));

        report.setStatus(newStatus);
        userReportDao.save(report);
    }


    @Override
    public List<UserReport> getReportsByStatus(UserReport.ReportStatus status) {

        return userReportDao.findByStatus(status);
    }

}
