package com.study_group_service.study_group_service.dto.user;

import com.study_group_service.study_group_service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime created_at;
    private Role role;
    private Long consecutiveAttendance;
    private Long roomCount;
    private LocalDate lastAttendanceDate;
}
