package com.study_group_service.study_group_service.event.study;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudyRoomEventRegistered(
        UUID userID, // 어떤 회원이 참여한건지 식별하기 위함
        String studyMeetingEventName,    // 스터디 이벤트 이름
        LocalDateTime when, // 원래 언제 모이기로 했는지,
        LocalDateTime joinedAt   // 참여 완료 처리가 된 시간
) {
}