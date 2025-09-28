package com.study_group_service.study_group_service.event.meeting;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 스터디 모임 참여 완료 이벤트
 * 
 * 사용자가 스터디 모임에 성공적으로 참여를 완료했을 때 발생하는 이벤트입니다.
 * 이 이벤트는 참여 완료를 다른 서비스들에게 알리고,
 * 참여 이력 관리 및 후속 처리를 수행할 수 있도록 합니다.
 * 
 * 주요 활용 사례:
 * - 참여 완료 알림 발송
 * - 출석 체크 및 참여 이력 기록
 * - 참여 통계 수집 및 분석
 * - 후속 처리 (설문조사, 피드백 요청 등)
 */
public record StudyMeetingParticipationCompleted(
    UUID studyGroupId,        // 스터디 그룹 ID
    UUID meetingId,           // 회의 ID
    UUID userId,              // 참여자 ID
    String studyGroupName,     // 스터디 그룹명
    String meetingName,       // 회의명
    LocalDateTime completedAt  // 참여 완료 시점
) {}
