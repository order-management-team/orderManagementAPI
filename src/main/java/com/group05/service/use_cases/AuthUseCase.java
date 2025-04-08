package com.group05.service.use_cases;

import org.springframework.security.core.Authentication;

import com.group05.dto.AuthRequestDTO;
import com.group05.dto.AuthResponseDTO;

public interface AuthUseCase {
    AuthResponseDTO login(AuthRequestDTO request);
    Authentication authenticate(String username, String password);
}
