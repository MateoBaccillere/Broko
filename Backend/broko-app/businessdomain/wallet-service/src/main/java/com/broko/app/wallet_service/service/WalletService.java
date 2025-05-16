package com.broko.app.wallet_service.service;

import com.broko.app.wallet_service.dto.TransferRequestDTO;
import com.broko.app.wallet_service.dto.WalletRequestDTO;
import com.broko.app.wallet_service.dto.WalletResponseDTO;

import java.util.List;
import java.util.UUID;

public interface WalletService {

    List<WalletResponseDTO> getWalletsByUser(UUID userId);
    WalletResponseDTO getWallet(UUID walletId);
    WalletResponseDTO createWallet(UUID userId, WalletRequestDTO dto);
    WalletResponseDTO updateWallet(UUID walletId, WalletRequestDTO dto);
    void deleteWallet(UUID walletId);
    void transferBetweenWallets(UUID userId, TransferRequestDTO transfer);
    java.math.BigDecimal getWalletBalance(UUID walletId);
}
