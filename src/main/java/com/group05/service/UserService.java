package com.group05.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.group05.dto.UserRequestDTO;
import com.group05.dto.UserResponseDTO;
import com.group05.mapper.UserMapper;
import com.group05.model.Role;
import com.group05.model.User;
import com.group05.repository.RoleRepository;
import com.group05.repository.UserRepository;
import com.group05.service.use_cases.UserUseCase;

@Service
public class UserService implements UserUseCase{
    private UserMapper userMapper;
    
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);

        Role role = roleRepository.findById(userRequestDTO.getRoleId())
                        .orElseThrow(() -> new RuntimeException("Role not found."));

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user = userRepository.save(user);

        return userMapper.toDto(user);
    }

}
