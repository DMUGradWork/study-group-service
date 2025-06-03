package com.study_group_service.study_group_service.mapper.users;

import com.study_group_service.study_group_service.entity.user.Admin;
import com.study_group_service.study_group_service.entity.user.Users;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public Admin toEntity(Users user) {
        return Admin.builder()
                .user(user)
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .build();
    }

    public Admin updateEntity(Admin admin, Users user) {
        return admin.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .build();
    }
}
