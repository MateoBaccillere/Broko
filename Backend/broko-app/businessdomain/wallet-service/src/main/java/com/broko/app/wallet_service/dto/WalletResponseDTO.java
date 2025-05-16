package com.broko.app.wallet_service.dto;

import com.broko.app.wallet_service.model.CurrencyType;
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
public class WalletResponseDTO {
    private UUID id;
    private UUID userId;
    private String name;
    private CurrencyType currency;
    private BigDecimal balance;
    private boolean active;
}
