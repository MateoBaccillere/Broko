package com.broko.app.dashboard_service.service;

import com.broko.app.dashboard_service.dto.MetricDTO;
import com.broko.app.dashboard_service.dto.SummaryDTO;

import java.util.List;
import java.util.UUID;

public interface DashboardService {

    SummaryDTO getUserSummary(UUID userId);

    List<MetricDTO> getUserMetrics(UUID userId, String period);

    byte[] exportUserData(UUID userId, String format);

    SummaryDTO getAdminOverview();
}
