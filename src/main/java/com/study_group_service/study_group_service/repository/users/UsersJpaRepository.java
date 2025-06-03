package com.study_group_service.study_group_service.repository.users;

import com.study_group_service.study_group_service.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersJpaRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email); // 중복 이메일 boolean 으로 분리
}
