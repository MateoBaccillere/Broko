package com.broko.app.wallet_service.mapper;

import com.broko.app.wallet_service.dto.WalletRequestDTO;
import com.broko.app.wallet_service.dto.WalletResponseDTO;
import com.broko.app.wallet_service.model.Wallet;

public class WalletMapper {

    public static Wallet toEntity(WalletRequestDTO dto) {
        return Wallet.builder()
                .name(dto.getName())
                .currency(dto.getCurrency())
                .balance(java.math.BigDecimal.ZERO)
                .active(true)
                .build();
    }

    public static WalletResponseDTO toDTO(Wallet wallet) {
        return WalletResponseDTO.builder()
                .id(wallet.getId())
                .userId(wallet.getUserId())
                .name(wallet.getName())
                .currency(wallet.getCurrency())
                .balance(wallet.getBalance())
                .active(wallet.isActive())
                .build();
    }
}
