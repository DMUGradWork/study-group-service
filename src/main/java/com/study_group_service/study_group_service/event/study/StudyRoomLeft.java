package com.study_group_service.study_group_service.event.study;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudyRoomLeft(
    UUID studyRoomId,         // 떠난 스터디룸 ID
    UUID userId,              // 떠난 사용자 ID
    String reason,            // 떠난 이유
    LocalDateTime leftAt      // 떠난 시점
) {}
