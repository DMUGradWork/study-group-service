package com.study_group_service.study_group_service.event.meeting;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudyMeetingEventParticipationRegistered(
    UUID studyGroupId,       // 스터디 그룹 식별자
    UUID meetingId,          // 모임 고유 식별자
    UUID userId,             // 참석자 식별자
    String studyGroupName,   // 스터디 그룹명
    String meetingName,      // 모임 이름
    String description,      // 모임 정보 설명
    LocalDateTime startAt,   // 모임 예정 시각 
    LocalDateTime endAt,     // 모임 예정 종료 시각
    LocalDateTime joinedAt   // 사용자가 참석을 누른 시각
) {}
