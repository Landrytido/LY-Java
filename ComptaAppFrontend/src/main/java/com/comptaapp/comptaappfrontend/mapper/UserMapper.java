package com.comptaapp.comptaappfrontend.mapper;

import com.comptaapp.comptaappfrontend.dto.UserDTO;
import com.comptaapp.comptaappfrontend.model.User;
import com.comptaapp.comptaappfrontend.model.Role;

import java.util.HashSet;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toModel(UserDTO dto) {
        User user = new User(dto.getUsername(), dto.getEmail(), dto.getPassword());
        user.setEnabled(dto.isEnabled());
        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            user.setRole(dto.getRoles().iterator().next().getName());
        }
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().addAll(dto.getRoles().stream()
                .map(roleDto -> new Role(roleDto.getId(), roleDto.getName()))
                .collect(Collectors.toSet()));
        return user;
    }

    public static UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setEnabled(user.isEnabled());
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(role -> {
                        UserDTO.RoleDto roleDto = new UserDTO.RoleDto();
                        roleDto.setId(role.getId());
                        roleDto.setName(role.getName());
                        return roleDto;
                    })
                    .collect(Collectors.toSet()));
        }
        return dto;
    }
}