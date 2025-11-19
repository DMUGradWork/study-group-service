package com.study_group_service.study_group_service.listener;

import com.study_group_service.study_group_service.event.user.UserProfileUpdated;
import com.study_group_service.study_group_service.event.user.UserRegistered;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 사용자 관련 이벤트 리스너
 * 
 * 사용자 등록, 프로필 업데이트 등의 이벤트를 처리합니다.
 */
@Slf4j
@Component
public class UserEventListener {

    /**
     * 사용자 등록 이벤트 처리
     */
    @EventListener
    public void handleUserRegistered(UserRegistered event) {
        log.info("사용자 등록 이벤트 처리: userId={}, nickname={}, createdAt={}", 
                event.userId(), event.nickname(), event.createdAt());
        
        // 여기에 사용자 등록 시 필요한 추가 로직을 구현할 수 있습니다
        // 예: 환영 이메일 발송, 기본 설정 초기화, 알림 발송 등
    }

    /**
     * 사용자 프로필 업데이트 이벤트 처리
     */
    @EventListener
    public void handleUserProfileUpdated(UserProfileUpdated event) {
        log.info("사용자 프로필 업데이트 이벤트 처리: userId={}, oldNickname={}, newNickname={}, updatedFields={}, updatedAt={}", 
                event.userId(), event.oldNickname(), event.newNickname(), 
                event.updatedFields(), event.updatedAt());
        
        // 여기에 프로필 업데이트 시 필요한 추가 로직을 구현할 수 있습니다
        // 예: 변경 사항 로깅, 관련 서비스에 알림, 캐시 무효화 등
    }
}
