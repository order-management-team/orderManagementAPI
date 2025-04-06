package com.group05.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group05.dto.UserRequestDTO;
import com.group05.dto.UserResponseDTO;
import com.group05.service.UserService;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.CREATED);
    }
}
