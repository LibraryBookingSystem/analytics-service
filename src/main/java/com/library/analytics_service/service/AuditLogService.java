package com.library.analytics_service.service;

import com.library.analytics_service.dto.AuditLogResponse;
import com.library.analytics_service.entity.AuditLog;
import com.library.analytics_service.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for audit log operations
 */
@Service
public class AuditLogService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuditLogService.class);
    
    private final AuditLogRepository auditLogRepository;
    
    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }
    
    /**
     * Create an audit log entry
     */
    @Transactional
    public AuditLog createAuditLog(Long userId, String username, String userRole,
                                   String actionType, String resourceType, Long resourceId,
                                   String resourceName, String description, String ipAddress,
                                   Boolean success, String errorMessage, String metadata) {
        logger.debug("Creating audit log: {} - {} - {}", actionType, resourceType, resourceId);
        
        AuditLog auditLog = new AuditLog(userId, username, userRole, actionType, resourceType,
                                        resourceId, description);
        auditLog.setResourceName(resourceName);
        auditLog.setIpAddress(ipAddress);
        auditLog.setSuccess(success != null ? success : true);
        auditLog.setErrorMessage(errorMessage);
        auditLog.setMetadata(metadata);
        
        auditLog = auditLogRepository.save(auditLog);
        return auditLog;
    }
    
    /**
     * Get audit logs with filters
     */
    public Page<AuditLogResponse> getAuditLogs(Long userId, String actionType, String resourceType,
                                               LocalDateTime startTime, LocalDateTime endTime,
                                               int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        
        Page<AuditLog> logs;
        
        if (userId != null || actionType != null || resourceType != null) {
            logs = auditLogRepository.findLogsWithFilters(userId, actionType, resourceType,
                                                          startTime, endTime, pageable);
        } else {
            logs = auditLogRepository.findLogsBetween(startTime, endTime, pageable);
        }
        
        return logs.map(AuditLogResponse::fromAuditLog);
    }
    
    /**
     * Get audit logs by user ID
     */
    public Page<AuditLogResponse> getAuditLogsByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<AuditLog> logs = auditLogRepository.findByUserId(userId, pageable);
        return logs.map(AuditLogResponse::fromAuditLog);
    }
    
    /**
     * Get audit logs by action type
     */
    public Page<AuditLogResponse> getAuditLogsByActionType(String actionType, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<AuditLog> logs = auditLogRepository.findByActionType(actionType, pageable);
        return logs.map(AuditLogResponse::fromAuditLog);
    }
    
    /**
     * Get failed audit logs (for security monitoring)
     */
    public List<AuditLogResponse> getFailedAuditLogs(LocalDateTime startTime, LocalDateTime endTime) {
        List<AuditLog> logs = auditLogRepository.findFailedLogsBetween(startTime, endTime);
        return logs.stream()
                .map(AuditLogResponse::fromAuditLog)
                .collect(Collectors.toList());
    }
}
