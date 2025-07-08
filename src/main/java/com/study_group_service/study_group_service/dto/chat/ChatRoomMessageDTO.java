package com.study_group_service.study_group_service.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChatRoomMessageDTO {
    @JsonProperty("chatRoomId")
    private Long chatRoomId;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("sender")
    private String sender;
    @JsonProperty("content")
    private String content;
    @JsonProperty("sentAt")
    private LocalDateTime sentAt;
    @JsonProperty("imageUrl")
    private String imageUrl;
}
