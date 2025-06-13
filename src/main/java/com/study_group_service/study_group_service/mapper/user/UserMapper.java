package com.study_group_service.study_group_service.mapper.user;

import com.study_group_service.study_group_service.dto.user.UserDTO;
import com.study_group_service.study_group_service.entity.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .created_at(user.getCreated_at())
                .role(user.getRole())
                .build();
    }

    public User toEntity(UserDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .phone(dto.getPhone())
                .created_at(dto.getCreated_at())
                .role(dto.getRole())
                .build();
    }

    public List<UserDTO> toDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
