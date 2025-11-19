package com.study_group_service.study_group_service.dto.users;

import com.study_group_service.study_group_service.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDTO {
    private String email;
    private String password;
    private String phone;
    private Role role;
    private LocalDateTime created_at;
}
