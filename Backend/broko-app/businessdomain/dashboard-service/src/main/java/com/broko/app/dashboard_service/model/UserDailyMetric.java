package com.broko.app.dashboard_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_daily_metrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDailyMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private UUID userId;

    private LocalDate date;

    @Column(name = "transaction_count")
    private int transactionCount;

    @Column(name = "total_volume")
    private BigDecimal totalVolume;

    @Column(name = "total_count")
    private BigDecimal totalSent;

    @Column(name = "total_received")
    private BigDecimal totalReceived;
}
