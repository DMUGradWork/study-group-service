package com.study_group_service.study_group_service.event.user;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 사용자 가입 이벤트
 * 
 * 새로운 사용자가 시스템에 가입했을 때 발생하는 이벤트입니다.
 * 이 이벤트는 사용자 서비스에서 발행되며, 다른 마이크로서비스들이
 * 새로운 사용자 정보를 인지하고 필요한 초기 설정을 수행할 수 있도록 합니다.
 * 
 * 주요 활용 사례:
 * - 웰컴 메시지 발송
 * - 사용자 프로필 초기화
 * - 권한 및 역할 설정
 * - 알림 서비스 구독 설정
 */
public record UserRegistered(
    UUID userId,              // 가입한 사용자 ID
    LocalDateTime createdAt,  // 가입 시점
    String nickname           // 기본 닉네임
) {}
