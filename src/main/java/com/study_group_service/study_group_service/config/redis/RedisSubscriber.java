package com.study_group_service.study_group_service.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study_group_service.study_group_service.dto.chat.ChatRoomMessageDTO;
import com.study_group_service.study_group_service.exception.chat.ChatMessageSendException;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.service.chat.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import static com.study_group_service.study_group_service.message.UtilMessage.showSend;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final ChatMessageService chatMessageService;
    private final ErrorMessage errorMessage;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String jsonMessage = new String(message.getBody());
            ChatRoomMessageDTO dto = objectMapper.readValue(jsonMessage, ChatRoomMessageDTO.class);
            log.info(showSend(), dto);

            chatMessageService.saveMessage(dto);

        } catch (Exception e) {
            throw new ChatMessageSendException(errorMessage.showSendMessageError());
        }
    }
}
