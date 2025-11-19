package com.study_group_service.study_group_service.service;

import com.study_group_service.study_group_service.dto.ChatRoomMessageDTO;

import java.util.Optional;

public interface ChatMessageService {
    void saveMessage(ChatRoomMessageDTO chatRoomMessageDTO);
    Optional<ChatRoomMessageDTO> getChatMessagesByRoomId(Long chatRoomId);
}
