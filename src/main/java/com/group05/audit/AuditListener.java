package com.group05.audit;

import com.group05.constant.RecordStateConstants;
import com.group05.service.use_cases.AuditUseCase;
import com.group05.util.BeanUtils;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class AuditListener {

    private AuditUseCase getAuditService() {
        return BeanUtils.getBean(AuditUseCase.class);
    }

    @PrePersist
    public void setCreated(Object entity) {
        if (entity instanceof ModelAudit audit) {
            AuditUseCase auditUseCase = getAuditService();
            audit.setUserCreated(auditUseCase.getUser());
            audit.setIpCreated(auditUseCase.getIpAddress());
            audit.setFlgState(RecordStateConstants.ACTIVE);
        }
    }

    @PreUpdate
    public void setUpdated(Object entity) {
        if (entity instanceof ModelAudit audit) {
            AuditUseCase auditUseCase = getAuditService();
            audit.setUserUpdated(auditUseCase.getUser());
            audit.setIpUpdated(auditUseCase.getIpAddress());
        }
    }
}
