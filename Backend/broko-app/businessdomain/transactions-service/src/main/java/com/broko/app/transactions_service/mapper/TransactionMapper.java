package com.broko.app.transactions_service.mapper;

import com.broko.app.transactions_service.dto.TransactionRequestDTO;
import com.broko.app.transactions_service.dto.TransactionResponseDTO;
import com.broko.app.transactions_service.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequestDTO request, UUID userId) {
        Transaction transaction = new Transaction();
        transaction.setSenderWalletId(request.getSenderWalletId());
        transaction.setReceiverWalletId(request.getReceiverWalletId());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setUserId(userId);
        return transaction;
    }

    public TransactionResponseDTO toResponse(Transaction transaction) {
        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setId(transaction.getId());
        response.setSenderWalletId(transaction.getSenderWalletId());
        response.setReceiverWalletId(transaction.getReceiverWalletId());
        response.setAmount(transaction.getAmount());
        response.setCurrency(transaction.getCurrency());
        response.setStatus(transaction.getStatus());
        response.setCreatedAt(transaction.getCreatedAt());
        response.setUserId(transaction.getUserId());
        return response;
    }

}
