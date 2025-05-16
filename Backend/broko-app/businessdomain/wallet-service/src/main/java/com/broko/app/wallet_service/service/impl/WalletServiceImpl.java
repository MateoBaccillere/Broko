package com.broko.app.wallet_service.service.impl;

import com.broko.app.wallet_service.dto.TransferRequestDTO;
import com.broko.app.wallet_service.dto.WalletRequestDTO;
import com.broko.app.wallet_service.dto.WalletResponseDTO;
import com.broko.app.wallet_service.exception.WalletNotFoundException;
import com.broko.app.wallet_service.mapper.WalletMapper;
import com.broko.app.wallet_service.model.Wallet;
import com.broko.app.wallet_service.repository.WalletRepository;
import com.broko.app.wallet_service.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    @Override
    public List<WalletResponseDTO> getWalletsByUser(UUID userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(WalletMapper::toDTO)
                .toList();
    }

    @Override
    public WalletResponseDTO getWallet(UUID walletId) {
        Wallet wallet = repository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        return WalletMapper.toDTO(wallet);
    }

    @Override
    public WalletResponseDTO createWallet(UUID userId, WalletRequestDTO dto) {
        Wallet wallet = WalletMapper.toEntity(dto);
        wallet.setUserId(userId);
        return WalletMapper.toDTO(repository.save(wallet));
    }

    @Override
    public WalletResponseDTO updateWallet(UUID walletId, WalletRequestDTO dto) {
        Wallet wallet = repository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        wallet.setName(dto.getName());
        wallet.setCurrency(dto.getCurrency());
        return WalletMapper.toDTO(repository.save(wallet));
    }

    @Override
    public void deleteWallet(UUID walletId) {
        repository.deleteById(walletId);
    }

    @Override
    @Transactional
    public void transferBetweenWallets(UUID userId, TransferRequestDTO transfer) {
        Wallet source = repository.findById(transfer.getSourceWalletId())
                .orElseThrow(() -> new WalletNotFoundException(transfer.getSourceWalletId()));
        Wallet target = repository.findById(transfer.getTargetWalletId())
                .orElseThrow(() -> new WalletNotFoundException(transfer.getTargetWalletId()));

        if (!source.getUserId().equals(userId)) {
            throw new IllegalArgumentException("No sos dueño de la wallet origen.");
        }

        if (source.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        source.setBalance(source.getBalance().subtract(transfer.getAmount()));
        target.setBalance(target.getBalance().add(transfer.getAmount()));

        repository.save(source);
        repository.save(target);
    }

    @Override
    public BigDecimal getWalletBalance(UUID walletId) {
        Wallet wallet = repository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId));
        return wallet.getBalance();
    }
}
