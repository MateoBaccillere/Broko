package com.broko.app.wallet_service.event;

import com.broko.app.wallet_service.model.CurrencyType;
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
public class WalletUpdateEvent {

    private UUID walletId;
    private UUID userId;
    private BigDecimal balance;
    private CurrencyType currency;
    private String operation; // "CREATED", "UPDATED", "TRANSFER"
    private String timestamp;
}
