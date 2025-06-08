package com.broko.app.transactions_service.kafka;

import com.broko.app.transactions_service.dto.TransactionCompletedEvent;
import com.broko.app.transactions_service.dto.TransactionInitiatedEvent;
import com.broko.app.transactions_service.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, TransactionInitiatedEvent> kafkaTemplate;
    private final KafkaTemplate<String, TransactionCompletedEvent> transactionCompletedKafkaTemplate;

    public void publishTransactionInitiatedEvent(Transaction transaction) {
        TransactionInitiatedEvent event = new TransactionInitiatedEvent(
                transaction.getId(),
                transaction.getSenderWalletId(),
                transaction.getReceiverWalletId(),
                transaction.getAmount(),
                transaction.getCurrency()
        );

        kafkaTemplate.send("transactions.initiated", transaction.getId().toString(), event);
    }


    public void publishTransactionCompletedEvent(Transaction transaction) {
        TransactionCompletedEvent event = new TransactionCompletedEvent(
                transaction.getId(),
                transaction.getSenderWalletId(),
                transaction.getReceiverWalletId(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getCreatedAt()
        );

        transactionCompletedKafkaTemplate.send("transactions.completed", transaction.getId().toString(), event);
    }

}
