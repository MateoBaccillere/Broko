package com.broko.app.transactions_service.kafka;

import com.broko.app.transactions_service.model.Transaction;
import com.broko.app.transactions_service.model.TransactionStatus;
import com.broko.app.transactions_service.persistence.TransactionRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionKafkaListener {

    private final TransactionRepository transactionRepository;
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "transactions.approved", groupId = "transaction-service")
    public void handleApproved(TransactionStatusEvent event) {
        updateTransactionStatus(event.getTransactionId(), TransactionStatus.COMPLETED);
    }

    @KafkaListener(topics = "transactions.rejected", groupId = "transaction-service")
    public void handleRejected(TransactionStatusEvent event) {
        updateTransactionStatus(event.getTransactionId(), TransactionStatus.FAILED);
    }

    private void updateTransactionStatus(UUID transactionId, TransactionStatus newStatus) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));

        transaction.setStatus(newStatus);
        transactionRepository.save(transaction);

        if (newStatus == TransactionStatus.COMPLETED) {
            kafkaProducer.publishTransactionCompletedEvent(transaction);
        }
    }
}
