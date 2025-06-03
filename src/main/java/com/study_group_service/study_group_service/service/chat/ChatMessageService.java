package com.study_group_service.study_group_service.service.chat;

import com.study_group_service.study_group_service.dto.chat.ChatRoomMessageDTO;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {
    void saveMessage(ChatRoomMessageDTO chatRoomMessageDTO);
    Optional<ChatRoomMessageDTO> getChatMessagesByRoomId(Long chatRoomId);
    List<ChatRoomMessageDTO> getAllMessagesByChatRoomId(Long chatRoomId);
}
