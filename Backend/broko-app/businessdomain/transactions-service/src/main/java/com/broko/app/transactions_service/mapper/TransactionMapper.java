package com.broko.app.transactions_service.mapper;

import com.broko.app.transactions_service.dto.TransactionRequestDTO;
import com.broko.app.transactions_service.dto.TransactionResponseDTO;
import com.broko.app.transactions_service.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequestDTO request) {
        Transaction transaction = new Transaction();
        transaction.setSenderWalletId(request.getSenderWalletId());
        transaction.setReceiverWalletId(request.getReceiverWalletId());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
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
        return response;
    }

}
