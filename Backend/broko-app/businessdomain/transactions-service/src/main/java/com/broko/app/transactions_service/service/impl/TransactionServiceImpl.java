package com.broko.app.transactions_service.service.impl;

import com.broko.app.transactions_service.dto.TransactionRequestDTO;
import com.broko.app.transactions_service.dto.TransactionResponseDTO;
import com.broko.app.transactions_service.mapper.TransactionMapper;
import com.broko.app.transactions_service.model.Transaction;
import com.broko.app.transactions_service.model.TransactionStatus;
import com.broko.app.transactions_service.persistence.TransactionRepository;
import com.broko.app.transactions_service.service.TransactionService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {


    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {
        validateTransactionRequest(request);

        Transaction transaction = transactionMapper.toEntity(request);

        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);

        // Simulación de lógica de negocio
        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);

        // TODO: publicar evento Kafka `transactions.completed`

        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponseDTO getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction not found"));
        return transactionMapper.toResponse(transaction);
    }

    private void validateTransactionRequest(TransactionRequestDTO request) {
        if (request.getSenderWalletId().equals(request.getReceiverWalletId())) {
            throw new IllegalArgumentException("Sender and receiver wallets must be different");
        }
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero");
        }
    }

}
