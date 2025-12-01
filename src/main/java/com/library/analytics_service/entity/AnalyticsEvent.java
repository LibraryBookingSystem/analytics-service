package com.library.analytics_service.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing an analytics event (booking created, canceled, etc.)
 */
@Entity
@Table(name = "analytics_events")
public class AnalyticsEvent {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "event_type", nullable = false)
    private String eventType; // booking.created, booking.canceled, etc.
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "resource_id")
    private Long resourceId;
    
    @Column(name = "booking_id")
    private Long bookingId;
    
    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;
    
    @Column(columnDefinition = "TEXT")
    private String metadata; // Additional event data as JSON
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (eventTime == null) {
            eventTime = LocalDateTime.now();
        }
    }
    
    // Constructors
    public AnalyticsEvent() {}
    
    public AnalyticsEvent(String eventType, Long userId, Long resourceId, Long bookingId) {
        this.eventType = eventType;
        this.userId = userId;
        this.resourceId = resourceId;
        this.bookingId = bookingId;
        this.eventTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
    
    public Long getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    
    public LocalDateTime getEventTime() {
        return eventTime;
    }
    
    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

