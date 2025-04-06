package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.UserRequestDTO;
import com.group05.dto.UserResponseDTO;
import com.group05.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = RoleMapper.class)
public interface UserMapper {
    UserResponseDTO toDto(User user);

    User toEntity(UserRequestDTO userRequestDTO);
}
