package com.study_group_service.study_group_service.mapper.users;

import com.study_group_service.study_group_service.dto.users.UsersDTO;
import com.study_group_service.study_group_service.entity.user.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersMapper {

    public UsersDTO toDto(Users users) {
        return UsersDTO.builder()
                .id(users.getId())
                .email(users.getEmail())
                .password(users.getPassword())
                .name(users.getName())
                .phone(users.getPhone())
                .created_at(users.getCreated_at())
                .role(users.getRole())
                .build();
    }

    public Users toEntity(UsersDTO dto) {
        return Users.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .phone(dto.getPhone())
                .created_at(dto.getCreated_at())
                .role(dto.getRole())
                .build();
    }

    public List<UsersDTO> toDto(List<Users> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
