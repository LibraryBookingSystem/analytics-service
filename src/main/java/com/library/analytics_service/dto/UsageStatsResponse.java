package com.library.analytics_service.dto;

import java.time.LocalDate;
import java.util.Map;

/**
 * DTO for usage statistics response
 */
public class UsageStatsResponse {
    
    private LocalDate date;
    private Long resourceId;
    private Integer totalBookings;
    private Integer completedBookings;
    private Integer canceledBookings;
    private Integer noShowBookings;
    private Long totalDurationMinutes;
    private Double utilizationPercentage;
    private Map<String, Object> peakHours;
    
    // Constructors
    public UsageStatsResponse() {}
    
    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public Long getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
    
    public Integer getTotalBookings() {
        return totalBookings;
    }
    
    public void setTotalBookings(Integer totalBookings) {
        this.totalBookings = totalBookings;
    }
    
    public Integer getCompletedBookings() {
        return completedBookings;
    }
    
    public void setCompletedBookings(Integer completedBookings) {
        this.completedBookings = completedBookings;
    }
    
    public Integer getCanceledBookings() {
        return canceledBookings;
    }
    
    public void setCanceledBookings(Integer canceledBookings) {
        this.canceledBookings = canceledBookings;
    }
    
    public Integer getNoShowBookings() {
        return noShowBookings;
    }
    
    public void setNoShowBookings(Integer noShowBookings) {
        this.noShowBookings = noShowBookings;
    }
    
    public Long getTotalDurationMinutes() {
        return totalDurationMinutes;
    }
    
    public void setTotalDurationMinutes(Long totalDurationMinutes) {
        this.totalDurationMinutes = totalDurationMinutes;
    }
    
    public Double getUtilizationPercentage() {
        return utilizationPercentage;
    }
    
    public void setUtilizationPercentage(Double utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
    }
    
    public Map<String, Object> getPeakHours() {
        return peakHours;
    }
    
    public void setPeakHours(Map<String, Object> peakHours) {
        this.peakHours = peakHours;
    }
}





