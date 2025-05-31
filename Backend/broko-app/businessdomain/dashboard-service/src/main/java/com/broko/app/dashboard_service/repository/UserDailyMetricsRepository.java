package com.broko.app.dashboard_service.repository;

import com.broko.app.dashboard_service.model.UserDailyMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserDailyMetricsRepository extends JpaRepository<UserDailyMetric,Long> {

    List<UserDailyMetric> findAllByUserIdAndDateBetween(UUID userId, LocalDate start, LocalDate end);
}
