package com.study_group_service.study_group_service.repository.user;

import com.study_group_service.study_group_service.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email); // 중복 이메일 boolean 으로 분리
}
