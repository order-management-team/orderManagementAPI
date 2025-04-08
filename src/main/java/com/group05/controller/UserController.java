package com.group05.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group05.dto.UserRequestDTO;
import com.group05.dto.UserResponseDTO;
import com.group05.service.UserServiceImpl;
import com.group05.service.use_cases.UserUseCase;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserUseCase userUseCase;

    public UserController(UserServiceImpl userService){
        this.userUseCase = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userUseCase.createUser(userRequestDTO), HttpStatus.CREATED);
    }
}
