package com.library.analytics_service.repository;

import com.library.analytics_service.entity.AnalyticsEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for AnalyticsEvent entity
 */
@Repository
public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, Long> {
    
    /**
     * Find events by type
     */
    List<AnalyticsEvent> findByEventType(String eventType);
    
    /**
     * Find events by resource ID
     */
    List<AnalyticsEvent> findByResourceId(Long resourceId);
    
    /**
     * Find events by user ID
     */
    List<AnalyticsEvent> findByUserId(Long userId);
    
    /**
     * Find events within date range
     */
    @Query("SELECT e FROM AnalyticsEvent e WHERE e.eventTime BETWEEN :startTime AND :endTime")
    List<AnalyticsEvent> findEventsBetween(@Param("startTime") LocalDateTime startTime,
                                          @Param("endTime") LocalDateTime endTime);
    
    /**
     * Count events by type within date range
     */
    @Query("SELECT e.eventType, COUNT(e) FROM AnalyticsEvent e WHERE e.eventTime BETWEEN :startTime AND :endTime GROUP BY e.eventType")
    List<Object[]> countEventsByTypeBetween(@Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime);
    
    /**
     * Get booking count by hour for a date range
     */
    @Query("SELECT EXTRACT(HOUR FROM e.eventTime) as hour, COUNT(e) as count " +
           "FROM AnalyticsEvent e WHERE e.eventType = 'booking.created' " +
           "AND e.eventTime BETWEEN :startTime AND :endTime " +
           "GROUP BY EXTRACT(HOUR FROM e.eventTime) " +
           "ORDER BY count DESC")
    List<Object[]> getPeakHours(@Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime);
}




