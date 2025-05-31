package com.broko.app.dashboard_service.listener;

import com.broko.app.dashboard_service.event.WalletUpdatedEvent;
import com.broko.app.dashboard_service.model.UserMetrics;
import com.broko.app.dashboard_service.repository.UserMetricsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletEventListener {

    private final UserMetricsRepository userMetricsRepo;

    @KafkaListener(
            topics = "wallet.updated",
            groupId = "dashboard-group",
            containerFactory = "walletUpdatedListenerFactory"
    )
    public void handle(WalletUpdatedEvent event) {
        UUID userId = event.getUserId();
        BigDecimal balance = event.getBalance();

        log.info("🟢 Evento recibido: WalletUpdatedEvent user={}, balance={}", userId, balance);

        UserMetrics metrics = userMetricsRepo.findById(userId)
                .orElseGet(() -> UserMetrics.builder()
                        .userId(userId)
                        .walletCount(0)
                        .totalBalance(BigDecimal.ZERO)
                        .totalSent(BigDecimal.ZERO)
                        .totalReceived(BigDecimal.ZERO)
                        .totalTransactions(0)
                        .build());

        metrics.setWalletCount(metrics.getWalletCount() + 1); // en real deberías sincronizar cantidad real
        metrics.setTotalBalance(metrics.getTotalBalance().add(balance));
        userMetricsRepo.save(metrics);
    }
}
