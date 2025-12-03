package com.library.analytics_service.controller;

import com.library.analytics_service.dto.UsageStatsResponse;
import com.library.analytics_service.service.AnalyticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller for analytics endpoints
 */
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    
    private final AnalyticsService analyticsService;
    
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }
    
    /**
     * Get utilization statistics
     * GET /api/analytics/utilization?startDate=2025-12-01&endDate=2025-12-31&resourceId=1
     */
    @GetMapping("/utilization")
    public ResponseEntity<List<UsageStatsResponse>> getUtilizationStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long resourceId) {
        List<UsageStatsResponse> stats = analyticsService.getUtilizationStats(startDate, endDate, resourceId);
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Get peak hours
     * GET /api/analytics/peak-hours?startTime=2025-12-01T00:00:00&endTime=2025-12-31T23:59:59
     */
    @GetMapping("/peak-hours")
    public ResponseEntity<Map<String, Object>> getPeakHours(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Map<String, Object> peakHours = analyticsService.getPeakHours(startTime, endTime);
        return ResponseEntity.ok(peakHours);
    }
    
    /**
     * Get overall statistics
     * GET /api/analytics/overall?startTime=2025-12-01T00:00:00&endTime=2025-12-31T23:59:59
     */
    @GetMapping("/overall")
    public ResponseEntity<Map<String, Object>> getOverallStats(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        Map<String, Object> stats = analyticsService.getOverallStats(startTime, endTime);
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Health check endpoint
     * GET /api/analytics/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Analytics Service is running!");
    }
}




