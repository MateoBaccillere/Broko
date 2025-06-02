package com.broko.app.transactions_service.dto;

import com.broko.app.transactions_service.model.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionResponseDTO {

    private UUID id;
    private UUID senderWalletId;
    private UUID receiverWalletId;
    private BigDecimal amount;
    private String currency;
    private TransactionStatus status;
    private LocalDateTime createdAt;
}
