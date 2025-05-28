package com.broko.app.dashboard_service.service.impl;

import com.broko.app.dashboard_service.dto.MetricDTO;
import com.broko.app.dashboard_service.dto.SummaryDTO;
import com.broko.app.dashboard_service.repository.UserMetricsRepository;
import com.broko.app.dashboard_service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserMetricsRepository repository;

    @Override
    public SummaryDTO getUserSummary(UUID userId) {
        return repository.findById(userId)
                .map(DashboardMapper::toSummaryDTO)
                .orElse(SummaryDTO.builder().walletCount(0).totalBalance(null).build());
    }

    @Override
    public List<MetricDTO> getUserMetrics(UUID userId, String period) {
        // Placeholder: acá iría lógica por fechas (ej: última semana, mes)
        return List.of(); // aún no implementado
    }

    @Override
    public byte[] exportUserData(UUID userId, String format) {
        // Simulación de exportación
        String csv = "campo1,campo2\nvalor1,valor2\n";
        return csv.getBytes();
    }

    @Override
    public SummaryDTO getAdminOverview() {
        // Placeholder: podés usar stats globales más adelante
        return SummaryDTO.builder()
                .walletCount(repository.findAll().size())
                .totalBalance(null)
                .totalSent(null)
                .totalReceived(null)
                .totalTransactions(0)
                .lastTransactionAt(null)
                .build();
    }
}
