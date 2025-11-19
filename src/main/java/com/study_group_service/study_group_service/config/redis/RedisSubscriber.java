package com.study_group_service.study_group_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study_group_service.study_group_service.dto.ChatRoomMessageDTO;
import com.study_group_service.study_group_service.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ChatMessageService chatMessageService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String jsonMessage = new String(message.getBody());
            ChatRoomMessageDTO dto = objectMapper.readValue(jsonMessage, ChatRoomMessageDTO.class);
            log.info("Redis 수신 {}", dto);

            chatMessageService.saveMessage(dto);

        } catch (Exception e) {
            log.error("Redis 수신 오류", e);
        }
    }
}
