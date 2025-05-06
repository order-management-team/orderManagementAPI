package com.group05.model;

import com.group05.audit.ModelAudit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "roles", schema = "order_mgmt")
@Schema(name = "Role", description = "Entidad que representa un rol asignado a un usuario dentro del sistema")
public class Role extends ModelAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del rol", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El nombre del rol no puede estar vacío")
    @Schema(description = "Nombre del rol (único)", example = "ADMIN")
    private String name;

    @Schema(description = "Descripción del rol", example = "Perfil de Administrador")
    private String description;
}
