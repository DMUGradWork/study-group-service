package com.study_group_service.study_group_service.repository.user;

import com.study_group_service.study_group_service.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUuid(UUID uuid);
    boolean existsByEmail(String email);
    boolean existsByUuid(UUID uuid);
}
