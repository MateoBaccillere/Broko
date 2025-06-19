package com.broko.app.transactions_service.controller;

import com.broko.app.transactions_service.dto.TransactionRequestDTO;
import com.broko.app.transactions_service.dto.TransactionResponseDTO;
import com.broko.app.transactions_service.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO request) {
        TransactionResponseDTO response = transactionService.createTransaction(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@PathVariable UUID id) {
        TransactionResponseDTO response = transactionService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-transaction")
    public ResponseEntity<List<TransactionResponseDTO>> getMyTransactions() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        List<TransactionResponseDTO> transactions = transactionService.getTransactionsByUserId(UUID.fromString(userId));
        return ResponseEntity.ok(transactions);
    }

}
