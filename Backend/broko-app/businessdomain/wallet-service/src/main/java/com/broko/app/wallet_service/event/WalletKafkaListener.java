package com.broko.app.wallet_service.event;

import com.broko.app.wallet_service.model.Wallet;
import com.broko.app.wallet_service.repository.WalletRepository;
import com.broko.events.TransactionInitiatedEvent;
import com.broko.events.TransactionStatusEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletKafkaListener {

    private final WalletRepository walletRepository;
    private final KafkaTemplate<String, TransactionStatusEvent> kafkaTemplate;

    @KafkaListener(topics = "transactions.initiated", groupId = "wallet-service")
    public void processTransactionInitiated(TransactionInitiatedEvent event) {
        try {
            Optional<Wallet> senderOpt = walletRepository.findById(event.getSenderWalletId());
            Optional<Wallet> receiverOpt = walletRepository.findById(event.getReceiverWalletId());

            if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
                publishRejected(event.getTransactionId());
                return;
            }

            Wallet sender = senderOpt.get();
            Wallet receiver = receiverOpt.get();

            if (!sender.getCurrency().equals(event.getCurrency()) ||
                    !receiver.getCurrency().equals(event.getCurrency())) {
                publishRejected(event.getTransactionId());
                return;
            }

            if (sender.getBalance().compareTo(event.getAmount()) < 0) {
                publishRejected(event.getTransactionId());
                return;
            }

            sender.setBalance(sender.getBalance().subtract(event.getAmount()));
            receiver.setBalance(receiver.getBalance().add(event.getAmount()));

            walletRepository.save(sender);
            walletRepository.save(receiver);

            publishApproved(event.getTransactionId());
        } catch (Exception e) {
            // Cualquier error inesperado: rechazamos la transacción
            publishRejected(event.getTransactionId());
        }
    }

    private void publishApproved(UUID transactionId) {
        kafkaTemplate.send("transactions.approved", transactionId.toString(), new TransactionStatusEvent(transactionId));
    }

    private void publishRejected(UUID transactionId) {
        kafkaTemplate.send("transactions.rejected", transactionId.toString(), new TransactionStatusEvent(transactionId));
    }
}
