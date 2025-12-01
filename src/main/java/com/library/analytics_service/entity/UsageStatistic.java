package com.library.analytics_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing daily usage statistics (aggregated data)
 */
@Entity
@Table(name = "usage_statistics", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"date", "resource_id"})
})
public class UsageStatistic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "resource_id")
    private Long resourceId;
    
    @Column(name = "total_bookings", nullable = false)
    private Integer totalBookings = 0;
    
    @Column(name = "completed_bookings", nullable = false)
    private Integer completedBookings = 0;
    
    @Column(name = "canceled_bookings", nullable = false)
    private Integer canceledBookings = 0;
    
    @Column(name = "no_show_bookings", nullable = false)
    private Integer noShowBookings = 0;
    
    @Column(name = "total_duration_minutes", nullable = false)
    private Long totalDurationMinutes = 0L;
    
    @Column(name = "utilization_percentage")
    private Double utilizationPercentage;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public UsageStatistic() {}
    
    public UsageStatistic(LocalDate date, Long resourceId) {
        this.date = date;
        this.resourceId = resourceId;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

