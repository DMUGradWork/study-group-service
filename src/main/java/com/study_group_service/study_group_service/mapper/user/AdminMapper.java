package com.study_group_service.study_group_service.mapper.user;

import com.study_group_service.study_group_service.entity.user.Admin;
import com.study_group_service.study_group_service.entity.user.User;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public Admin toEntity(User user) {
        return Admin.builder()
                .user(user)
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .build();
    }

    public Admin updateEntity(Admin admin, User user) {
        return admin.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .build();
    }
}
