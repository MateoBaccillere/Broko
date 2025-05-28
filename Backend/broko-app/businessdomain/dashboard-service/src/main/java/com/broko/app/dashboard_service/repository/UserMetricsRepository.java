package com.broko.app.dashboard_service.repository;

import com.broko.app.dashboard_service.model.UserMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserMetricsRepository extends JpaRepository<UserMetrics, UUID> {
}
