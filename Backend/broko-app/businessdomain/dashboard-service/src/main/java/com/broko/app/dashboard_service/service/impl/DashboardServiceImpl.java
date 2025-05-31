package com.broko.app.dashboard_service.service.impl;

import com.broko.app.dashboard_service.dto.MetricDTO;
import com.broko.app.dashboard_service.dto.SummaryDTO;
import com.broko.app.dashboard_service.mapper.DashboardMapper;
import com.broko.app.dashboard_service.model.UserDailyMetric;
import com.broko.app.dashboard_service.model.UserMetrics;
import com.broko.app.dashboard_service.repository.UserDailyMetricsRepository;
import com.broko.app.dashboard_service.repository.UserMetricsRepository;
import com.broko.app.dashboard_service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserMetricsRepository repository;
    private final UserDailyMetricsRepository dailyMetricRepo;

    @Override
    public SummaryDTO getUserSummary(UUID userId) {
        return repository.findById(userId)
                .map(DashboardMapper::toSummaryDTO)
                .orElse(SummaryDTO.builder().walletCount(0).totalBalance(null).build());
    }

    @Override
    public List<MetricDTO> getUserMetrics(UUID userId, String period) {
        LocalDate end = LocalDate.now();
        LocalDate start;

        switch (period.toLowerCase()) {
            case "weekly" -> start = end.minusDays(7);
            case "daily" -> start = end;
            default -> start = end.minusMonths(1); // fallback mensual
        }

        var list = dailyMetricRepo.findAllByUserIdAndDateBetween(userId, start, end);

        return list.stream().map(metric ->
                MetricDTO.builder()
                        .date(metric.getDate())
                        .volume(metric.getTotalVolume())
                        .transactionCount(metric.getTransactionCount())
                        .build()
        ).toList();
    }

    @Override
    public byte[] exportUserData(UUID userId, String format) {
        if (!format.equalsIgnoreCase("csv")) {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }

        List<UserDailyMetric> metrics = dailyMetricRepo.findAllByUserIdAndDateBetween(
                userId,
                LocalDate.now().minusDays(30),
                LocalDate.now()
        );

        StringBuilder csv = new StringBuilder("date,totalVolume,transactionCount,totalSent,totalReceived\n");

        for (UserDailyMetric metric : metrics) {
            csv.append(metric.getDate()).append(",")
                    .append(metric.getTotalVolume()).append(",")
                    .append(metric.getTransactionCount()).append(",")
                    .append(metric.getTotalSent()).append(",")
                    .append(metric.getTotalReceived()).append("\n");
        }

        return csv.toString().getBytes();
    }


    @Override
    public SummaryDTO getAdminOverview() {
        List<UserMetrics> all = repository.findAll();

        int walletCount = all.stream().mapToInt(UserMetrics::getWalletCount).sum();
        int totalTransactions = all.stream().mapToInt(UserMetrics::getTotalTransactions).sum();

        BigDecimal totalBalance = all.stream()
                .map(UserMetrics::getTotalBalance)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSent = all.stream()
                .map(UserMetrics::getTotalSent)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalReceived = all.stream()
                .map(UserMetrics::getTotalReceived)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Instant lastTransaction = all.stream()
                .map(UserMetrics::getLastTransactionAt)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);

        return SummaryDTO.builder()
                .walletCount(walletCount)
                .totalBalance(totalBalance)
                .totalSent(totalSent)
                .totalReceived(totalReceived)
                .totalTransactions(totalTransactions)
                .lastTransactionAt(lastTransaction)
                .build();
    }

}
