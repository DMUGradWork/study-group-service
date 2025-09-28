package com.study_group_service.study_group_service.event.study;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 스터디룸 참여 이벤트
 * 
 * 사용자가 스터디룸에 참여했을 때 발생하는 이벤트입니다.
 * 이 이벤트는 참여 완료를 다른 서비스들에게 알리고,
 * 참여자에게 필요한 권한과 리소스를 제공할 수 있도록 합니다.
 * 
 * 주요 활용 사례:
 * - 참여 알림 발송 (참여자 및 기존 멤버들에게)
 * - 참여자 권한 설정 (채팅방 접근, 파일 공유 등)
 * - 참여자 목록 업데이트
 * - 스터디룸 통계 업데이트 (참여자 수 등)
 */
public record StudyRoomJoined(
    UUID studyRoomId,         // 참여한 스터디룸 ID
    UUID userId,              // 참여한 사용자 ID
    String role,              // 사용자 역할
    LocalDateTime joinedAt    // 참여 시점
) {}
