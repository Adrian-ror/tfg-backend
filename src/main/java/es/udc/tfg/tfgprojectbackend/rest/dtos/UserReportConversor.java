package es.udc.tfg.tfgprojectbackend.rest.dtos;

import es.udc.tfg.tfgprojectbackend.model.entities.UserReport;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class UserReportConversor {

    public static UserReportDto toUserReportDto(UserReport report) {
        return new UserReportDto(
                report.getId(),
                report.getReportedProduct() != null ? report.getReportedProduct().getId() : null,
                report.getReportedUser() != null ? report.getReportedUser().getId() : null,
                report.getReportType(),
                report.getDescription(),
                report.getStatus(),
                toMillis(report.getReportedAt())
        );
    }

    public static List<UserReportDto> toUserReportDtos(List<UserReport> reports) {
        return reports.stream()
                .map(UserReportConversor::toUserReportDto)
                .collect(Collectors.toList());
    }

    private static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES)
                .atZone(ZoneOffset.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
