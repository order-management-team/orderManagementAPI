package com.group05.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.group05.dto.RoleResponseDTO;
import com.group05.mapper.RoleMapper;
import com.group05.repository.RoleRepository;
import com.group05.service.use_cases.RoleUseCase;


@Service
public class RoleService implements RoleUseCase {

    private RoleMapper roleMapper;

    private RoleRepository roleRepository;

    public RoleService(RoleMapper roleMapper, RoleRepository roleRepository){
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponseDTO> getRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .toList();
    }

}
