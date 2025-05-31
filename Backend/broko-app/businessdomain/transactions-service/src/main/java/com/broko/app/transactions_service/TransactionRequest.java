package com.broko.app.transactions_service;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionRequest {
    @NotNull
    private UUID senderWalletId;

    @NotNull
    private UUID receiverWalletId;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank
    private String currency;
}
