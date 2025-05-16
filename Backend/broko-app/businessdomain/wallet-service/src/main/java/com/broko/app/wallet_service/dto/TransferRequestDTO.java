package com.broko.app.wallet_service.dto;

import jakarta.validation.constraints.NotNull;
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
public class TransferRequestDTO {
    @NotNull
    private UUID sourceWalletId;

    @NotNull
    private UUID targetWalletId;

    @NotNull
    private BigDecimal amount;
}
