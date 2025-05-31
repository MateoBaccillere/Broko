package com.broko.app.dashboard_service.mapper;

import com.broko.app.dashboard_service.dto.SummaryDTO;
import com.broko.app.dashboard_service.model.UserMetrics;

public class DashboardMapper {

    public static SummaryDTO toSummaryDTO(UserMetrics metrics) {
        return SummaryDTO.builder()
                .walletCount(metrics.getWalletCount())
                .totalBalance(metrics.getTotalBalance())
                .totalTransactions(metrics.getTotalTransactions())
                .totalSent(metrics.getTotalSent())
                .totalReceived(metrics.getTotalReceived())
                .lastTransactionAt(metrics.getLastTransactionAt())
                .build();
    }
}
