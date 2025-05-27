package com.broko.app.wallet_service.controller;

import com.broko.app.wallet_service.dto.TransferRequestDTO;
import com.broko.app.wallet_service.dto.WalletRequestDTO;
import com.broko.app.wallet_service.dto.WalletResponseDTO;
import com.broko.app.wallet_service.exception.WalletNotFoundException;
import com.broko.app.wallet_service.mapper.WalletMapper;
import com.broko.app.wallet_service.model.Wallet;
import com.broko.app.wallet_service.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;


    @GetMapping
    public ResponseEntity<List<WalletResponseDTO>> getMyWallets(@RequestHeader("X-User-Id") UUID userId) {
        var wallets = walletService.getWalletsByUser(userId);
        return ResponseEntity.ok(wallets);
    }
    @PostMapping
    public ResponseEntity<WalletResponseDTO> create(@RequestHeader("X-User-Id") UUID userId,
                                                    @Valid @RequestBody WalletRequestDTO dto) {
        WalletResponseDTO wallet = walletService.createWallet(userId, dto);
        return ResponseEntity.created(URI.create("/api/wallets/" + wallet.getId())).body(wallet);
    }
    @GetMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> getOne(@PathVariable UUID walletId) {
        WalletResponseDTO wallet = walletService.getWallet(walletId);

        return ResponseEntity.ok(wallet);
    }
    @PutMapping("/{walletId}")
    public ResponseEntity<WalletResponseDTO> update(@PathVariable UUID walletId,
                                                    @Valid @RequestBody WalletRequestDTO dto) {
        WalletResponseDTO updated = walletService.updateWallet(walletId, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{walletId}")
    public ResponseEntity<Void> delete(@PathVariable UUID walletId) {
        walletService.deleteWallet(walletId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{walletId}/transfer")
    public ResponseEntity<Void> transfer(@RequestHeader("X-User-Id") UUID userId,
                                         @Valid @RequestBody TransferRequestDTO dto) {
        walletService.transferBetweenWallets(userId, dto);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/{walletId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable UUID walletId) {
        BigDecimal balance = walletService.getWalletBalance(walletId);
        return ResponseEntity.ok(balance);
    }
}
