package com.study_group_service.study_group_service.event.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserRegistered(
    UUID userId,              // 가입한 사용자 ID
    LocalDateTime createdAt,  // 가입 시점
    String nickname           // 기본 닉네임
) {}
