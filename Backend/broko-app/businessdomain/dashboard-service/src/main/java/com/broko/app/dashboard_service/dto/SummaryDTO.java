package com.broko.app.dashboard_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SummaryDTO {

    private int walletCount;
    private BigDecimal totalBalance;
    private int totalTransactions;
    private BigDecimal totalSent;
    private BigDecimal totalReceived;
    private Instant lastTransactionAt;

}
