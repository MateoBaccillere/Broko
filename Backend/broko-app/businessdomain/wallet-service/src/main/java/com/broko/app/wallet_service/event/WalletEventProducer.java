package com.broko.app.wallet_service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.broko.app.wallet_service.event.WalletUpdateEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletEventProducer {

    private final KafkaTemplate<String, WalletUpdateEvent> kafkaTemplate;
    private static final String TOPIC = "wallet.updated";

    public void publish(WalletUpdateEvent event) {
        log.info("📤 Publicando evento a Kafka: {}", event);
        kafkaTemplate.send(TOPIC, event.getWalletId().toString(), event);
    }
}
