package com.library.analytics_service.listener;

import com.library.analytics_service.config.RabbitMQConfig;
import com.library.analytics_service.service.AuditLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitMQ listener for audit events
 */
@Component
public class AuditEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(AuditEventListener.class);
    
    private final AuditLogService auditLogService;
    
    public AuditEventListener(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }
    
    /**
     * Listen to audit.log events
     */
    @RabbitListener(queues = RabbitMQConfig.AUDIT_LOG_QUEUE)
    public void handleAuditLog(Map<String, Object> auditEvent) {
        try {
            logger.debug("Received audit event: {}", auditEvent.get("actionType"));
            
            Long userId = auditEvent.get("userId") != null ? 
                Long.valueOf(auditEvent.get("userId").toString()) : null;
            String username = auditEvent.get("username") != null ? 
                auditEvent.get("username").toString() : null;
            String userRole = auditEvent.get("userRole") != null ? 
                auditEvent.get("userRole").toString() : null;
            String actionType = auditEvent.get("actionType") != null ? 
                auditEvent.get("actionType").toString() : "OTHER";
            String resourceType = auditEvent.get("resourceType") != null ? 
                auditEvent.get("resourceType").toString() : null;
            Long resourceId = auditEvent.get("resourceId") != null ? 
                Long.valueOf(auditEvent.get("resourceId").toString()) : null;
            String resourceName = auditEvent.get("resourceName") != null ? 
                auditEvent.get("resourceName").toString() : null;
            String description = auditEvent.get("description") != null ? 
                auditEvent.get("description").toString() : null;
            String ipAddress = auditEvent.get("ipAddress") != null ? 
                auditEvent.get("ipAddress").toString() : null;
            Boolean success = auditEvent.get("success") != null ? 
                Boolean.valueOf(auditEvent.get("success").toString()) : true;
            String errorMessage = auditEvent.get("errorMessage") != null ? 
                auditEvent.get("errorMessage").toString() : null;
            
            // Serialize metadata
            String metadata = null;
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                metadata = mapper.writeValueAsString(auditEvent);
            } catch (Exception e) {
                logger.warn("Failed to serialize audit event metadata: {}", e.getMessage());
            }
            
            auditLogService.createAuditLog(userId, username, userRole, actionType, resourceType,
                                          resourceId, resourceName, description, ipAddress,
                                          success, errorMessage, metadata);
            
        } catch (Exception e) {
            logger.error("Failed to process audit event: {}", e.getMessage(), e);
        }
    }
}


