package com.broko.app.dashboard_service.controller;

import com.broko.app.dashboard_service.dto.MetricDTO;
import com.broko.app.dashboard_service.dto.SummaryDTO;
import com.broko.app.dashboard_service.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestControllerAdvice
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public ResponseEntity<SummaryDTO> getSummary(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(dashboardService.getUserSummary(userId));
    }

    @GetMapping("/metrics")
    public ResponseEntity<List<MetricDTO>> getMetrics(@RequestHeader("X-User-Id") UUID userId,
                                                      @RequestParam(defaultValue = "monthly") String period) {
        return ResponseEntity.ok(dashboardService.getUserMetrics(userId, period));
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(@RequestHeader("X-User-Id") UUID userId,
                                         @RequestParam(defaultValue = "csv") String format) {
        byte[] file = dashboardService.exportUserData(userId, format);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.attachment().filename("dashboard-export." + format).build());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().headers(headers).body(file);
    }

    @GetMapping("/admin/dashboard")
    public ResponseEntity<SummaryDTO> getAdminOverview() {
        return ResponseEntity.ok(dashboardService.getAdminOverview());
    }
}
