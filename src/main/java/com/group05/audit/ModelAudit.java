package com.group05.audit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class ModelAudit extends DateAudit {

    @Schema(description = "Usuario que registró")
    @JsonIgnore
    @Column(name = "user_created", updatable = false)
    private String userCreated;

    @Schema(description = "Usuario que modificó")
    @JsonIgnore
    @Column(name = "user_updated", insertable = false)
    private String userUpdated;

    @Schema(description = "IP desde donde se registró")
    @JsonIgnore
    @Column(name = "ip_created", updatable = false)
    private String ipCreated;

    @Schema(description = "IP desde donde se modificó")
    @JsonIgnore
    @Column(name = "ip_updated", insertable = false)
    private String ipUpdated;

    @Schema(description = "Estado lógico: 1 activo, 0 eliminado")
    @Column(name = "flg_state", length = 1)
    private String flgState;
}
