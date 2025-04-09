package com.group05.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema="order_mgmt")
@Schema(name="users", description = "Entidad que representa a un usuario del sistema")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del usuario", example = "1")
    private Long id;

    @Column
    @Schema(description = "Nombre del usuario", example = "Juan")
    private String name;

    @Email(message = "El formato de correo no es válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Column(unique = true)
    @Schema(description = "Correo electrónico del usuario (único)", example = "juan@gmail.com", required = true)
    private String email;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()]).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, un número y un símbolo especial")
    @Column
    @Schema(description = "Contraseña del usuario (encriptada)", example = "$2a$10$....")
    private String password;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Schema(description = "Fecha de registro del usuario", example = "08-04-2025")
    private LocalDate signUpDate;

    @Column
    @Schema(description = "Monto total gastado por el usuario", example = "105.50")
    private Double totalSpent;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @Schema(description = "Rol asignado al usuario")
    private Role role;

    @PrePersist
    public void prePersist() {
        this.signUpDate = LocalDate.now();
        this.totalSpent = 0.0;
    }

}