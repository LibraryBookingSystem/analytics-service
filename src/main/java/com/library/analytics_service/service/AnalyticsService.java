package com.library.analytics_service.service;

import com.library.analytics_service.dto.UsageStatsResponse;
import com.library.analytics_service.entity.AnalyticsEvent;
import com.library.analytics_service.entity.UsageStatistic;
import com.library.analytics_service.repository.AnalyticsEventRepository;
import com.library.analytics_service.repository.UsageStatisticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service layer for analytics operations
 */
@Service
public class AnalyticsService {
    
    private static final Logger logger = LoggerFactory.getLogger(AnalyticsService.class);
    
    private final AnalyticsEventRepository eventRepository;
    private final UsageStatisticRepository statisticRepository;
    
    public AnalyticsService(AnalyticsEventRepository eventRepository,
                           UsageStatisticRepository statisticRepository) {
        this.eventRepository = eventRepository;
        this.statisticRepository = statisticRepository;
    }
    
    /**
     * Record an analytics event
     */
    @Transactional
    public void recordEvent(String eventType, Long userId, Long resourceId, Long bookingId) {
        logger.info("Recording analytics event: {} for booking: {}", eventType, bookingId);
        
        AnalyticsEvent event = new AnalyticsEvent(eventType, userId, resourceId, bookingId);
        eventRepository.save(event);
        
        // Update daily statistics
        updateDailyStatistics(resourceId, eventType);
    }
    
    /**
     * Get utilization statistics for a date range
     */
    public List<UsageStatsResponse> getUtilizationStats(LocalDate startDate, LocalDate endDate, Long resourceId) {
        List<UsageStatistic> statistics;
        
        if (resourceId != null) {
            statistics = statisticRepository.findStatisticsByResourceBetween(resourceId, startDate, endDate);
        } else {
            statistics = statisticRepository.findStatisticsBetween(startDate, endDate);
        }
        
        return statistics.stream()
            .map(this::toUsageStatsResponse)
            .collect(Collectors.toList());
    }
    
    /**
     * Get peak hours for a date range
     */
    public Map<String, Object> getPeakHours(LocalDateTime startTime, LocalDateTime endTime) {
        List<Object[]> peakHours = eventRepository.getPeakHours(startTime, endTime);
        
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> hours = new ArrayList<>();
        
        for (Object[] row : peakHours) {
            Map<String, Object> hourData = new HashMap<>();
            hourData.put("hour", ((Number) row[0]).intValue());
            hourData.put("bookingCount", ((Number) row[1]).longValue());
            hours.add(hourData);
        }
        
        result.put("peakHours", hours);
        
        if (!hours.isEmpty()) {
            Map<String, Object> peakHour = hours.get(0);
            result.put("peakHour", peakHour.get("hour"));
            result.put("peakBookingCount", peakHour.get("bookingCount"));
        }
        
        return result;
    }
    
    /**
     * Get overall statistics
     */
    public Map<String, Object> getOverallStats(LocalDateTime startTime, LocalDateTime endTime) {
        List<Object[]> eventCounts = eventRepository.countEventsByTypeBetween(startTime, endTime);
        
        Map<String, Object> stats = new HashMap<>();
        long totalBookings = 0;
        long completedBookings = 0;
        long canceledBookings = 0;
        long noShowBookings = 0;
        
        for (Object[] row : eventCounts) {
            String eventType = (String) row[0];
            long count = ((Number) row[1]).longValue();
            
            switch (eventType) {
                case "booking.created":
                    totalBookings += count;
                    break;
                case "booking.checked_in":
                    completedBookings += count;
                    break;
                case "booking.canceled":
                    canceledBookings += count;
                    break;
                case "booking.no_show":
                    noShowBookings += count;
                    break;
            }
        }
        
        stats.put("totalBookings", totalBookings);
        stats.put("completedBookings", completedBookings);
        stats.put("canceledBookings", canceledBookings);
        stats.put("noShowBookings", noShowBookings);
        
        if (totalBookings > 0) {
            stats.put("completionRate", (double) completedBookings / totalBookings * 100);
            stats.put("cancelationRate", (double) canceledBookings / totalBookings * 100);
            stats.put("noShowRate", (double) noShowBookings / totalBookings * 100);
        }
        
        return stats;
    }
    
    /**
     * Update daily statistics for a resource
     */
    @Transactional
    private void updateDailyStatistics(Long resourceId, String eventType) {
        if (resourceId == null) {
            return;
        }
        
        LocalDate today = LocalDate.now();
        UsageStatistic stat = statisticRepository.findByDateAndResourceId(today, resourceId)
            .orElse(new UsageStatistic(today, resourceId));
        
        switch (eventType) {
            case "booking.created":
                stat.setTotalBookings(stat.getTotalBookings() + 1);
                break;
            case "booking.checked_in":
            case "booking.completed":
                stat.setCompletedBookings(stat.getCompletedBookings() + 1);
                break;
            case "booking.canceled":
                stat.setCanceledBookings(stat.getCanceledBookings() + 1);
                break;
            case "booking.no_show":
                stat.setNoShowBookings(stat.getNoShowBookings() + 1);
                break;
        }
        
        statisticRepository.save(stat);
    }
    
    /**
     * Convert UsageStatistic entity to UsageStatsResponse DTO
     */
    private UsageStatsResponse toUsageStatsResponse(UsageStatistic stat) {
        UsageStatsResponse response = new UsageStatsResponse();
        response.setDate(stat.getDate());
        response.setResourceId(stat.getResourceId());
        response.setTotalBookings(stat.getTotalBookings());
        response.setCompletedBookings(stat.getCompletedBookings());
        response.setCanceledBookings(stat.getCanceledBookings());
        response.setNoShowBookings(stat.getNoShowBookings());
        response.setTotalDurationMinutes(stat.getTotalDurationMinutes());
        response.setUtilizationPercentage(stat.getUtilizationPercentage());
        return response;
    }
}




