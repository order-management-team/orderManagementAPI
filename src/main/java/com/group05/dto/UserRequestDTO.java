package com.group05.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private Long id;

    private String name;

    private String email;

    private String password;

    private Long roleId;
}
