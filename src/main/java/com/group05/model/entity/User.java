package com.group05.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")
public class User {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Email(message = "El formato de correo no es válido")
    @NotBlank(message = "El correo no puede estar vacío")
    @Column(unique = true)
    private String email;
    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Pattern(regexp = ".*[0-9].*", message = "La contraseña debe contener al menos un número")
    @Pattern(regexp = ".*[!@#$%^&*()].*", message = "La contraseña debe contener al menos un símbolo especial")
    @Column
    private String password;
    @Column
    private LocalDate signUpDate;
    @Column
    private Double totalSpent;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference(value = "user-role")
    private Role role;
}