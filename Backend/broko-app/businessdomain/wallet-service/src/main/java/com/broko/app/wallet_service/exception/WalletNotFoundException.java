package com.broko.app.wallet_service.exception;

import java.util.UUID;

public class WalletNotFoundException extends BusinessException{
    public WalletNotFoundException(UUID walletId) {
        super("WALLET_NOT_FOUND", "No se encontró la wallet con ID: " + walletId);
    }
}
