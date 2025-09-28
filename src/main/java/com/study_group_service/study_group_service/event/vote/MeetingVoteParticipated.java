package com.study_group_service.study_group_service.event.vote;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 회의 투표 참여 이벤트
 * 
 * 사용자가 회의 투표에 참여했을 때 발생하는 이벤트입니다.
 * 이 이벤트는 투표 참여를 다른 서비스들에게 알리고,
 * 투표 결과를 실시간으로 업데이트할 수 있도록 합니다.
 * 
 * 주요 활용 사례:
 * - 투표 결과 실시간 업데이트
 * - 참여율 통계 수집 및 분석
 * - 중복 투표 방지 처리
 * - 투표 완료 알림 발송
 */
public record MeetingVoteParticipated(
    UUID voteId,              // 투표 ID
    UUID participantId,       // 참여자 ID
    String voteOption,        // 투표 선택지
    LocalDateTime votedAt     // 투표 시점
) {}
