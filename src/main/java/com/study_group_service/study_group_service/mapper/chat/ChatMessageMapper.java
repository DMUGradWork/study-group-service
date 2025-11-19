package com.study_group_service.study_group_service.mapper.chat;
import com.study_group_service.study_group_service.dto.chat.ChatRoomMessageDTO;
import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import com.study_group_service.study_group_service.entity.chat.ChatRoomMessages;
import com.study_group_service.study_group_service.entity.user.Users;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ChatRoomMessageMapper {

    public ChatRoomMessageDTO toDto(ChatRoomMessages message) {
        return ChatRoomMessageDTO.builder()
                .chatRoomId(message.getChatRoom().getId())
                .userId(message.getUser().getId())
                .sender(message.getSender())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }

    public ChatRoomMessages toEntity(ChatRoomMessageDTO dto, ChatRoom chatRoom, Users user) {
        return ChatRoomMessages.builder()
                .chatRoom(chatRoom)
                .user(user)
                .sender(dto.getSender())
                .content(dto.getContent())
                .sentAt(dto.getSentAt())
                .build();
    }

    public Optional<ChatRoomMessageDTO> toDtoOptional(Optional<ChatRoomMessages> messages) {
        return messages.map(this::toDto);
    }
}
