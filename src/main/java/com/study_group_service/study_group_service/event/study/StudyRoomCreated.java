package com.study_group_service.study_group_service.event.study;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudyRoomCreated(
    UUID studyRoomId,         // 생성된 스터디룸 ID
    UUID creatorId,           // 스터디룸 생성자 ID
    String title,             // 스터디룸 제목
    String description,       // 스터디룸 설명
    LocalDateTime createdAt   // 생성 시점
) {}
