package com.study_group_service.study_group_service.event.vote;

import java.time.LocalDateTime;
import java.util.UUID;

public record MeetingVoteCreated(
    UUID voteId,              // 투표 ID
    UUID studyRoomId,         // 스터디룸 ID
    UUID creatorId,           // 투표 생성자 ID
    String title,             // 투표 제목
    String description,       // 투표 설명
    LocalDateTime startTime,  // 투표 시작 시간
    LocalDateTime endTime,    // 투표 종료 시간
    LocalDateTime createdAt   // 생성 시점
) {}
