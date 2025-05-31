package com.broko.app.dashboard_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCompletedEvent {

    private UUID transactionId;
    private UUID userId;
    private BigDecimal amount;
    private String currency;
    private String type;
}
