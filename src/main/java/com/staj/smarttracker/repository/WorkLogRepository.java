package com.staj.smarttracker.repository;

import com.staj.smarttracker.dto.FeatureAnalyticsDto;
import com.staj.smarttracker.dto.UserAnalyticsDto;
import com.staj.smarttracker.entity.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long>, JpaSpecificationExecutor<WorkLog> {


    @Override
    @EntityGraph(attributePaths = {"user"})
    Page<WorkLog> findAll(Specification<WorkLog> spec, Pageable pageable);


    @Query("SELECT new com.staj.smarttracker.dto.UserAnalyticsDto(w.user.email, SUM(w.spentHours)) " +
            "FROM WorkLog w GROUP BY w.user.email")
    List<UserAnalyticsDto> getUserAnalytics();

    @Query("SELECT new com.staj.smarttracker.dto.FeatureAnalyticsDto(w.feature.title, w.project.name, SUM(w.spentHours)) " +
            "FROM WorkLog w GROUP BY w.feature.title, w.project.name")
    List<FeatureAnalyticsDto> getFeatureAnalytics();

    @Query("""
    SELECT new com.staj.smarttracker.dto.FeatureUserEffortDto(
        w.feature.title,
        w.project.name,
        w.user.email,
        SUM(w.spentHours)
    )
    FROM WorkLog w
    GROUP BY w.feature.title, w.project.name, w.user.email
    ORDER BY w.feature.title, SUM(w.spentHours) DESC
    """)
    List<com.staj.smarttracker.dto.FeatureUserEffortDto> getFeatureUserEffortAnalytics();
}