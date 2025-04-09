package com.group05.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group05.dto.AuthRequestDTO;
import com.group05.dto.AuthResponseDTO;
import com.group05.service.use_cases.AuthUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Servicios relacionados a la autenticación del usuario")
public class AuthController {

    private AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase){
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    @Operation(
        method = "POST", 
        summary = "Validar credenciales", 
        description = "Servicio encargado de validar las credenciales de acceso del usuario",
        tags = {"Authentication"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AuthRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Succesful Authentication",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponseDTO.class)
                )
            )
        }
    )
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        AuthResponseDTO authResponseDTO = authUseCase.login(authRequestDTO);
        return ResponseEntity.ok(authResponseDTO);
    }
}
