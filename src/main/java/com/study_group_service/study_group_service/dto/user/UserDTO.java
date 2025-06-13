package com.study_group_service.study_group_service.dto.user;

import com.study_group_service.study_group_service.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime created_at;
    private Role role;
}
