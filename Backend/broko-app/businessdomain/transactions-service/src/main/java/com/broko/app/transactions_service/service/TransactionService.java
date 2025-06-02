package com.broko.app.transactions_service.service;

import com.broko.app.transactions_service.dto.TransactionRequestDTO;
import com.broko.app.transactions_service.dto.TransactionResponseDTO;

import java.util.UUID;

public interface TransactionService {
    TransactionResponseDTO createTransaction(TransactionRequestDTO request);
    TransactionResponseDTO getTransactionById(UUID id);
}
