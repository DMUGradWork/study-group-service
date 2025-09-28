package com.study_group_service.study_group_service.event.meeting;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudyMeetingRescheduled(
    UUID studyGroupId,       
    UUID meetingId,
    String newMeetingName,
    String newDescription,
    LocalDateTime newStartAt,     // 변경된 시작 시각
    LocalDateTime newEndAt,       // 변경된 종료 시각
    LocalDateTime updatedAt       // 이벤트 발행 시각
) {}
