package com.group05.service.use_cases;

import com.group05.dto.UserRequestDTO;
import com.group05.dto.UserResponseDTO;

public interface UserUseCase {
    UserResponseDTO createUser(UserRequestDTO userDTO);
}
