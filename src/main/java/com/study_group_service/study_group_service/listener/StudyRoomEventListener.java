package com.study_group_service.study_group_service.listener;

import com.study_group_service.study_group_service.event.study.StudyRoomCreated;
import com.study_group_service.study_group_service.event.study.StudyRoomJoined;
import com.study_group_service.study_group_service.event.study.StudyRoomLeft;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 스터디룸 관련 이벤트 리스너
 * 
 * 스터디룸 생성, 참여, 퇴장 등의 이벤트를 처리합니다.
 */
@Slf4j
@Component
public class StudyRoomEventListener {

    /**
     * 스터디룸 생성 이벤트 처리
     */
    @EventListener
    public void handleStudyRoomCreated(StudyRoomCreated event) {
        log.info("스터디룸 생성 이벤트 처리: studyRoomId={}, creatorId={}, title={}, description={}, createdAt={}", 
                event.studyRoomId(), event.creatorId(), event.title(), 
                event.description(), event.createdAt());
        
        // 여기에 스터디룸 생성 시 필요한 추가 로직을 구현할 수 있습니다
        // 예: 생성자에게 알림 발송, 스터디룸 목록 업데이트, 통계 업데이트 등
    }

    /**
     * 스터디룸 참여 이벤트 처리
     */
    @EventListener
    public void handleStudyRoomJoined(StudyRoomJoined event) {
        log.info("스터디룸 참여 이벤트 처리: studyRoomId={}, userId={}, role={}, joinedAt={}", 
                event.studyRoomId(), event.userId(), event.role(), event.joinedAt());
        
        // 여기에 스터디룸 참여 시 필요한 추가 로직을 구현할 수 있습니다
        // 예: 참여자에게 권한 부여, 기존 멤버들에게 알림, 참여자 수 업데이트 등
    }

    /**
     * 스터디룸 퇴장 이벤트 처리
     */
    @EventListener
    public void handleStudyRoomLeft(StudyRoomLeft event) {
        log.info("스터디룸 퇴장 이벤트 처리: studyRoomId={}, userId={}, reason={}, leftAt={}", 
                event.studyRoomId(), event.userId(), event.reason(), event.leftAt());
        
        // 여기에 스터디룸 퇴장 시 필요한 추가 로직을 구현할 수 있습니다
        // 예: 권한 회수, 기존 멤버들에게 알림, 참여자 수 업데이트 등
    }
}
