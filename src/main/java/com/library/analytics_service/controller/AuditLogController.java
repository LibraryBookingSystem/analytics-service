package com.library.analytics_service.controller;

import com.library.analytics_service.dto.AuditLogResponse;
import com.library.analytics_service.service.AuditLogService;
import com.library.common.security.annotation.RequiresRole;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for audit log endpoints
 * Uses AOP annotations for RBAC authorization
 */
@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {
    
    private final AuditLogService auditLogService;
    
    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }
    
    /**
     * Get audit logs with filters
     * GET /api/audit-logs?userId=1&actionType=CREATE&resourceType=RESOURCE&startTime=...&endTime=...&page=0&size=20
     * Authorization: ADMIN only
     */
    @GetMapping
    @RequiresRole({"ADMIN"})
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogs(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String actionType,
            @RequestParam(required = false) String resourceType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        // Default to last 7 days if no time range specified
        if (startTime == null) {
            startTime = LocalDateTime.now().minusDays(7);
        }
        if (endTime == null) {
            endTime = LocalDateTime.now();
        }
        
        Page<AuditLogResponse> logs = auditLogService.getAuditLogs(
            userId, actionType, resourceType, startTime, endTime, page, size);
        
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get audit logs by user ID
     * GET /api/audit-logs/user/{userId}?page=0&size=20
     * Authorization: ADMIN only
     */
    @GetMapping("/user/{userId}")
    @RequiresRole({"ADMIN"})
    public ResponseEntity<Page<AuditLogResponse>> getAuditLogsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        
        Page<AuditLogResponse> logs = auditLogService.getAuditLogsByUserId(userId, page, size);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get failed audit logs (for security monitoring)
     * GET /api/audit-logs/failed?startTime=...&endTime=...
     * Authorization: ADMIN only
     */
    @GetMapping("/failed")
    @RequiresRole({"ADMIN"})
    public ResponseEntity<List<AuditLogResponse>> getFailedAuditLogs(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        
        // Default to last 24 hours if no time range specified
        if (startTime == null) {
            startTime = LocalDateTime.now().minusHours(24);
        }
        if (endTime == null) {
            endTime = LocalDateTime.now();
        }
        
        List<AuditLogResponse> logs = auditLogService.getFailedAuditLogs(startTime, endTime);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Health check endpoint
     * GET /api/audit-logs/health
     * Authorization: PUBLIC
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Audit Logs API is running!");
    }
}


