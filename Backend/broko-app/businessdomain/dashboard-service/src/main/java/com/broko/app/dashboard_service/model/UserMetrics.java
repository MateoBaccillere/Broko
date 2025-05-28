package com.broko.app.dashboard_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMetrics {
    @Id
    private UUID userId;

    private int walletCount;

    private BigDecimal totalBalance;

    private int totalTransactions;

    private BigDecimal totalSent;

    private BigDecimal totalReceived;

    private Instant lastTransactionAt;
}
