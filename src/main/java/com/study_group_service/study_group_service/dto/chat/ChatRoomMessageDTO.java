package com.study_group_service.study_group_service.dto.chat;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

public class ChatRoomMessageDTO {
    private Long chatRoomId;
    private Long userId;
    private String sender;
    private String content;
    private LocalDateTime sentAt;
}
