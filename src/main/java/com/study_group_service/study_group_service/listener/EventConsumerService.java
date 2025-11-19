package com.study_group_service.study_group_service.listener;

import com.study_group_service.study_group_service.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class EventConsumerService {

    // Study Room Events Consumer
    @RabbitListener(queues = RabbitMQConfig.STUDY_ROOM_QUEUE)
    public void handleStudyRoomEvent(Map<String, Object> event) {
        log.info("Received study room event: {}", event);
        
        String eventType = (String) event.get("eventType");
        switch (eventType) {
            case "StudyRoomCreated":
                handleStudyRoomCreated(event);
                break;
            case "StudyRoomJoined":
                handleStudyRoomJoined(event);
                break;
            case "StudyRoomLeft":
                handleStudyRoomLeft(event);
                break;
            default:
                log.warn("Unknown study room event type: {}", eventType);
        }
    }

    private void handleStudyRoomCreated(Map<String, Object> event) {
        log.info("Processing StudyRoomCreated event: {}", event);
        // 여기에 스터디룸 생성 이벤트 처리 로직 추가
    }

    private void handleStudyRoomJoined(Map<String, Object> event) {
        log.info("Processing StudyRoomJoined event: {}", event);
        // 여기에 스터디룸 참여 이벤트 처리 로직 추가
    }

    private void handleStudyRoomLeft(Map<String, Object> event) {
        log.info("Processing StudyRoomLeft event: {}", event);
        // 여기에 스터디룸 나가기 이벤트 처리 로직 추가
    }

    // User Events Consumer
    @RabbitListener(queues = RabbitMQConfig.USER_PROFILE_QUEUE)
    public void handleUserEvent(Map<String, Object> event) {
        log.info("Received user event: {}", event);
        
        String eventType = (String) event.get("eventType");
        switch (eventType) {
            case "UserRegistered":
                handleUserRegistered(event);
                break;
            case "UserProfileUpdated":
                handleUserProfileUpdated(event);
                break;
            default:
                log.warn("Unknown user event type: {}", eventType);
        }
    }

    private void handleUserRegistered(Map<String, Object> event) {
        log.info("Processing UserRegistered event: {}", event);
        // 여기에 사용자 등록 이벤트 처리 로직 추가
    }

    private void handleUserProfileUpdated(Map<String, Object> event) {
        log.info("Processing UserProfileUpdated event: {}", event);
        // 여기에 사용자 프로필 업데이트 이벤트 처리 로직 추가
    }

    // Community Events Consumer
    @RabbitListener(queues = RabbitMQConfig.COMMUNITY_POST_QUEUE)
    public void handleCommunityEvent(Map<String, Object> event) {
        log.info("Received community event: {}", event);
        
        String eventType = (String) event.get("eventType");
        switch (eventType) {
            case "CommunityPostCreated":
                handleCommunityPostCreated(event);
                break;
            case "CommunityCommentCreated":
                handleCommunityCommentCreated(event);
                break;
            default:
                log.warn("Unknown community event type: {}", eventType);
        }
    }

    private void handleCommunityPostCreated(Map<String, Object> event) {
        log.info("Processing CommunityPostCreated event: {}", event);
        // 여기에 커뮤니티 포스트 생성 이벤트 처리 로직 추가
    }

    private void handleCommunityCommentCreated(Map<String, Object> event) {
        log.info("Processing CommunityCommentCreated event: {}", event);
        // 여기에 커뮤니티 댓글 생성 이벤트 처리 로직 추가
    }

    // Chat Events Consumer
    @RabbitListener(queues = RabbitMQConfig.CHAT_MESSAGE_QUEUE)
    public void handleChatEvent(Map<String, Object> event) {
        log.info("Received chat event: {}", event);
        
        String eventType = (String) event.get("eventType");
        switch (eventType) {
            case "ChatMessageSent":
                handleChatMessageSent(event);
                break;
            default:
                log.warn("Unknown chat event type: {}", eventType);
        }
    }

    private void handleChatMessageSent(Map<String, Object> event) {
        log.info("Processing ChatMessageSent event: {}", event);
        // 여기에 채팅 메시지 전송 이벤트 처리 로직 추가
    }
}
