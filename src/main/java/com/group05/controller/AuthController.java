package com.group05.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group05.dto.AuthRequestDTO;
import com.group05.dto.AuthResponseDTO;
import com.group05.service.use_cases.AuthUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase){
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        AuthResponseDTO authResponseDTO = authUseCase.login(authRequestDTO);
        return ResponseEntity.ok(authResponseDTO);
    }
}
