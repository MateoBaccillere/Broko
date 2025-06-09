package com.broko.app.dashboard_service.listener;

import com.broko.app.dashboard_service.service.DashboardService;
import com.broko.events.TransactionCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardKafkaListener {

    private final DashboardService metricService;

    @KafkaListener(topics = "transactions.completed", groupId = "dashboard-service")
    public void handleTransactionCompleted(TransactionCompletedEvent event) {
        metricService.registerTransaction(event);
    }
}
