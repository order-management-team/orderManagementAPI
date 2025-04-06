package com.group05.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", schema="order_mgmt")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Email(message = "El formato de correo no es válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()]).{8,}$", message = "La contraseña debe tener al menos 8 caracteres, un número y un símbolo especial")
    @Column
    private String password;

    @Column
    private LocalDate signUpDate;

    @Column
    private Double totalSpent;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @PrePersist
    public void prePersist() {
        this.signUpDate = LocalDate.now();
        this.totalSpent = 0.0;
    }

}