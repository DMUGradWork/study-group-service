package com.study_group_service.study_group_service.config.redis;

import com.study_group_service.study_group_service.dto.chat.ChatRoomMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(String channel, ChatRoomMessageDTO message) {
        redisTemplate.convertAndSend(channel, message);
    }
}
