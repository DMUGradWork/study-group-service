package com.study_group_service.study_group_service.repository.user;

import com.study_group_service.study_group_service.entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminJpaRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}