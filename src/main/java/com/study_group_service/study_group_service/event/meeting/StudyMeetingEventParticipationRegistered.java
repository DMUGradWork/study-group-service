package com.study_group_service.study_group_service.event.meeting;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 스터디 모임 참석 신청 이벤트
 * 
 * 사용자가 스터디 모임에 참석을 신청했을 때 발생하는 이벤트입니다.
 * 이 이벤트는 참석 신청을 다른 서비스들에게 알리고,
 * 참석자에게 필요한 정보와 알림을 제공할 수 있도록 합니다.
 * 
 * 주요 활용 사례:
 * - 참석 신청 알림 발송 (신청자 및 모임 주최자에게)
 * - 참석자 목록 관리
 * - 모임 일정 알림 설정
 * - 참석 통계 수집 및 분석
 */
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
