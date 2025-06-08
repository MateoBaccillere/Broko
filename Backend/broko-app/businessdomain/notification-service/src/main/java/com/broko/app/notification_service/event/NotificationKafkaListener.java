package com.broko.app.notification_service.event;

import com.broko.app.notification_service.dto.TransactionCompletedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationKafkaListener {

    @KafkaListener(topics = "transactions.completed", groupId = "notification-service")
    public void handleTransactionCompleted(TransactionCompletedEvent event) {
        // Aquí enviarías correo, push, lo que sea
        System.out.printf("✅ Notificando transacción completada: %s%n", event.getTransactionId());
    }
}
