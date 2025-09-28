package com.study_group_service.study_group_service.event.chat;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatMessageSent(
    UUID messageId,           // 메시지 ID
    UUID chatRoomId,          // 채팅방 ID
    UUID senderId,            // 발신자 ID
    String content,           // 메시지 내용
    String messageType,       // 메시지 타입 (TEXT, IMAGE, FILE 등)
    LocalDateTime sentAt      // 발송 시점
) {}
