package com.library.analytics_service.dto;

import com.library.analytics_service.entity.AuditLog;
import java.time.LocalDateTime;

/**
 * DTO for audit log responses
 */
public class AuditLogResponse {
    
    private Long id;
    private Long userId;
    private String username;
    private String userRole;
    private String actionType;
    private String resourceType;
    private Long resourceId;
    private String resourceName;
    private String description;
    private String ipAddress;
    private Boolean success;
    private String errorMessage;
    private LocalDateTime timestamp;
    private String metadata;
    
    public AuditLogResponse() {}
    
    public static AuditLogResponse fromAuditLog(AuditLog auditLog) {
        AuditLogResponse response = new AuditLogResponse();
        response.setId(auditLog.getId());
        response.setUserId(auditLog.getUserId());
        response.setUsername(auditLog.getUsername());
        response.setUserRole(auditLog.getUserRole());
        response.setActionType(auditLog.getActionType());
        response.setResourceType(auditLog.getResourceType());
        response.setResourceId(auditLog.getResourceId());
        response.setResourceName(auditLog.getResourceName());
        response.setDescription(auditLog.getDescription());
        response.setIpAddress(auditLog.getIpAddress());
        response.setSuccess(auditLog.getSuccess());
        response.setErrorMessage(auditLog.getErrorMessage());
        response.setTimestamp(auditLog.getTimestamp());
        response.setMetadata(auditLog.getMetadata());
        return response;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUserRole() {
        return userRole;
    }
    
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    public String getActionType() {
        return actionType;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    
    public String getResourceType() {
        return resourceType;
    }
    
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    
    public Long getResourceId() {
        return resourceId;
    }
    
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
    
    public String getResourceName() {
        return resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    
    public Boolean getSuccess() {
        return success;
    }
    
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}


