package com.broko.app.dashboard_service.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletUpdatedEvent {
    private UUID walletId;
    private UUID userId;
    private BigDecimal balance;
    private String currency;
    private String operation;
}
