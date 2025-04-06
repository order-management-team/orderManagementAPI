package com.group05.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.group05.dto.RoleRequestDTO;
import com.group05.dto.RoleResponseDTO;
import com.group05.model.Role;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {
    RoleResponseDTO toDto(Role role);

    Role toEntity(RoleRequestDTO roleRequestDTO);
}
