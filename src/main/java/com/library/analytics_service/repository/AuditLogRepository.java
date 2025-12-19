package com.library.analytics_service.repository;

import com.library.analytics_service.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for AuditLog entity
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    /**
     * Find audit logs by user ID
     */
    Page<AuditLog> findByUserId(Long userId, Pageable pageable);
    
    /**
     * Find audit logs by action type
     */
    Page<AuditLog> findByActionType(String actionType, Pageable pageable);
    
    /**
     * Find audit logs by resource type
     */
    Page<AuditLog> findByResourceType(String resourceType, Pageable pageable);
    
    /**
     * Find audit logs within date range
     */
    @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :startTime AND :endTime")
    Page<AuditLog> findLogsBetween(@Param("startTime") LocalDateTime startTime,
                                   @Param("endTime") LocalDateTime endTime,
                                   Pageable pageable);
    
    /**
     * Find audit logs with filters
     */
    @Query("SELECT a FROM AuditLog a WHERE " +
           "(:userId IS NULL OR a.userId = :userId) AND " +
           "(:actionType IS NULL OR a.actionType = :actionType) AND " +
           "(:resourceType IS NULL OR a.resourceType = :resourceType) AND " +
           "a.timestamp BETWEEN :startTime AND :endTime " +
           "ORDER BY a.timestamp DESC")
    Page<AuditLog> findLogsWithFilters(@Param("userId") Long userId,
                                       @Param("actionType") String actionType,
                                       @Param("resourceType") String resourceType,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime,
                                       Pageable pageable);
    
    /**
     * Find audit logs by username
     */
    Page<AuditLog> findByUsername(String username, Pageable pageable);
    
    /**
     * Find failed audit logs (for security monitoring)
     */
    @Query("SELECT a FROM AuditLog a WHERE a.success = false AND a.timestamp BETWEEN :startTime AND :endTime")
    List<AuditLog> findFailedLogsBetween(@Param("startTime") LocalDateTime startTime,
                                        @Param("endTime") LocalDateTime endTime);
}
