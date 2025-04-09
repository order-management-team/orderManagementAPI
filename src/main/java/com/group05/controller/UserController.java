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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuarios", description = "Servicios relacionados a la gestión de usuarios")
public class UserController {

    private UserUseCase userUseCase;

    public UserController(UserServiceImpl userService){
        this.userUseCase = userService;
    }

    @PostMapping("/register")
      @Operation(
        method = "POST", 
        summary = "Registrar usuarios", 
        description = "Servicio encargado de registar usuarios",
        tags = {"Register"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserRequestDTO.class)
            )
        ),
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Created",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponseDTO.class)
                )
            )
        }
    )
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity<>(userUseCase.createUser(userRequestDTO), HttpStatus.CREATED);
    }
}
