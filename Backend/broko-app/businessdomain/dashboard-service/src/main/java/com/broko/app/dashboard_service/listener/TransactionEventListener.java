package com.broko.app.dashboard_service.listener;

import com.broko.app.dashboard_service.event.TransactionCompletedEvent;
import com.broko.app.dashboard_service.model.UserDailyMetric;
import com.broko.app.dashboard_service.model.UserMetrics;
import com.broko.app.dashboard_service.repository.UserDailyMetricsRepository;
import com.broko.app.dashboard_service.repository.UserMetricsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionEventListener {
    private final UserMetricsRepository userMetricsRepo;
    private final UserDailyMetricsRepository dailyMetricRepo;

    @KafkaListener(
            topics = "transactions.completed",
            groupId = "dashboard-group",
            containerFactory = "transactionCompletedListenerFactory"
    )
    public void handle(TransactionCompletedEvent event) {
        UUID userId = event.getUserId();
        BigDecimal amount = event.getAmount();

        log.info("🟢 Evento recibido: TransactionCompletedEvent user={}, amount={}", userId, amount);

        // Actualizar resumen global
        UserMetrics metrics = userMetricsRepo.findById(userId)
                .orElseGet(() -> UserMetrics.builder()
                        .userId(userId)
                        .walletCount(0)
                        .totalBalance(BigDecimal.ZERO)
                        .totalSent(BigDecimal.ZERO)
                        .totalReceived(BigDecimal.ZERO)
                        .totalTransactions(0)
                        .build());

        metrics.setTotalTransactions(metrics.getTotalTransactions() + 1);
        metrics.setTotalSent(metrics.getTotalSent().add(amount));
        metrics.setLastTransactionAt(Instant.now());
        userMetricsRepo.save(metrics);

        // Actualizar métrica diaria
        LocalDate today = LocalDate.now();
        UserDailyMetric daily = dailyMetricRepo
                .findAllByUserIdAndDateBetween(userId, today, today)
                .stream()
                .findFirst()
                .orElse(UserDailyMetric.builder()
                        .userId(userId)
                        .date(today)
                        .totalVolume(BigDecimal.ZERO)
                        .totalSent(BigDecimal.ZERO)
                        .totalReceived(BigDecimal.ZERO)
                        .transactionCount(0)
                        .build());

        daily.setTransactionCount(daily.getTransactionCount() + 1);
        daily.setTotalVolume(daily.getTotalVolume().add(amount));
        daily.setTotalSent(daily.getTotalSent().add(amount));
        dailyMetricRepo.save(daily);
    }
}
