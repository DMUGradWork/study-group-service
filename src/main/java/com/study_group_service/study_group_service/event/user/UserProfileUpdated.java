package com.study_group_service.study_group_service.event.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfileUpdated(
    UUID userId,              // 사용자 ID
    String oldNickname,       // 이전 닉네임
    String newNickname,       // 새 닉네임
    String updatedFields,     // 업데이트된 필드들 (JSON 형태)
    LocalDateTime updatedAt   // 업데이트 시점
) {}
