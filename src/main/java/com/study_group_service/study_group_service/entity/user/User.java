package com.study_group_service.study_group_service.entity.user;

import com.study_group_service.study_group_service.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String name;
    private String phone;
    private LocalDateTime created_at = LocalDateTime.now();

    @Enumerated(EnumType.ORDINAL)
    private Role role = Role.USER;

    public void promoteToAdmin() {
        this.role = Role.ADMIN;
    }
}
