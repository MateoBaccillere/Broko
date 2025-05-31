package com.broko.app.dashboard_service.config;

import com.broko.app.dashboard_service.event.TransactionCompletedEvent;
import com.broko.app.dashboard_service.event.WalletUpdatedEvent;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public Map<String, Object> consumerConfigs() {
        return Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
                ConsumerConfig.GROUP_ID_CONFIG, "dashboard-group",
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                JsonDeserializer.TRUSTED_PACKAGES, "*"
        );
    }

    @Bean
    public ConsumerFactory<String, TransactionCompletedEvent> transactionCompletedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                (Deserializer) new StringDeserializer(),
                new JsonDeserializer<>(TransactionCompletedEvent.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TransactionCompletedEvent> transactionCompletedListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, TransactionCompletedEvent>();
        factory.setConsumerFactory(transactionCompletedConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, WalletUpdatedEvent> walletUpdatedConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                consumerConfigs(),
                (Deserializer) new StringDeserializer(),
                new JsonDeserializer<>(WalletUpdatedEvent.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WalletUpdatedEvent> walletUpdatedListenerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, WalletUpdatedEvent>();
        factory.setConsumerFactory(walletUpdatedConsumerFactory());
        return factory;
    }
}
