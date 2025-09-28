package com.study_group_service.study_group_service.event.meeting;

import java.util.UUID;

/**
 * 스터디 모임 참석 취소 이벤트
 * 
 * 사용자가 스터디 모임 참석을 취소했을 때 발생하는 이벤트입니다.
 * 이 이벤트는 참석 취소를 다른 서비스들에게 알리고,
 * 관련된 리소스와 권한을 정리할 수 있도록 합니다.
 * 
 * 주요 활용 사례:
 * - 참석 취소 알림 발송 (취소자 및 모임 주최자에게)
 * - 참석자 목록에서 제거
 * - 참석 통계 업데이트
 * - 취소 이력 추적 및 분석
 */
public record StudyMeetingParticipationCancelled(
    UUID studyGroupId,
    UUID meetingId,
    UUID userId
) {}
