package com.study_group_service.study_group_service.service;

import com.study_group_service.study_group_service.config.RabbitMQConfig;
import com.study_group_service.study_group_service.event.chat.ChatMessageSent;
import com.study_group_service.study_group_service.event.community.CommunityCommentCreated;
import com.study_group_service.study_group_service.event.community.CommunityPostCreated;
import com.study_group_service.study_group_service.event.study.StudyRoomCreated;
import com.study_group_service.study_group_service.event.study.StudyRoomJoined;
import com.study_group_service.study_group_service.event.study.StudyRoomLeft;
import com.study_group_service.study_group_service.event.user.UserProfileUpdated;
import com.study_group_service.study_group_service.event.user.UserRegistered;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;

    // Study Room Events
    public void publishStudyRoomCreated(StudyRoomCreated event) {
        log.info("Publishing StudyRoomCreated event: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.STUDY_EVENTS_EXCHANGE,
                RabbitMQConfig.STUDY_ROOM_CREATED,
                event
        );
    }

    public void publishStudyRoomJoined(StudyRoomJoined event) {
        log.info("Publishing StudyRoomJoined event: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.STUDY_EVENTS_EXCHANGE,
                RabbitMQConfig.STUDY_ROOM_JOINED,
                event
        );
    }

    public void publishStudyRoomLeft(StudyRoomLeft event) {
        log.info("Publishing StudyRoomLeft event: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.STUDY_EVENTS_EXCHANGE,
                RabbitMQConfig.STUDY_ROOM_LEFT,
                event
        );
    }

    // User Events
    public void publishUserRegistered(UserRegistered event) {
        log.info("Publishing UserRegistered event: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.USER_EVENTS_EXCHANGE,
                RabbitMQConfig.USER_REGISTERED,
                event
        );
    }

    public void publishUserProfileUpdated(UserProfileUpdated event) {
        log.info("Publishing UserProfileUpdated event: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.USER_EVENTS_EXCHANGE,
                RabbitMQConfig.USER_PROFILE_UPDATED,
                event
        );
    }

    // Community Events
    public void publishCommunityPostCreated(CommunityPostCreated event) {
        log.info("Publishing CommunityPostCreated event: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.COMMUNITY_EVENTS_EXCHANGE,
                RabbitMQConfig.COMMUNITY_POST_CREATED,
                event
        );
    }

    public void publishCommunityCommentCreated(CommunityCommentCreated event) {
        log.info("Publishing CommunityCommentCreated event: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.COMMUNITY_EVENTS_EXCHANGE,
                RabbitMQConfig.COMMUNITY_COMMENT_CREATED,
                event
        );
    }

    // Chat Events
    public void publishChatMessageSent(ChatMessageSent event) {
        log.info("Publishing ChatMessageSent event: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CHAT_EVENTS_EXCHANGE,
                RabbitMQConfig.CHAT_MESSAGE_SENT,
                event
        );
    }
}
