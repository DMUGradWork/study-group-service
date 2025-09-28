package com.study_group_service.study_group_service.event.meeting;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudyMeetingParticipationCompleted(
    UUID studyGroupId,        // 스터디 그룹 ID
    UUID meetingId,           // 회의 ID
    UUID userId,              // 참여자 ID
    String studyGroupName,     // 스터디 그룹명
    String meetingName,       // 회의명
    LocalDateTime completedAt  // 참여 완료 시점
) {}
