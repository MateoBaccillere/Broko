package com.broko.app.wallet_service.dto;

import com.broko.app.wallet_service.model.CurrencyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletRequestDTO {
    @NotBlank
    private String name;

    @NotNull
    private CurrencyType currency;
}
