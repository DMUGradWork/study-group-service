package com.study_group_service.study_group_service.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ChatRoomMessageDTO implements Serializable {
    private Long chatRoomId;
    private Long userId;
    private String sender;
    private String content;
    private LocalDateTime sentAt;
}
