package com.study_group_service.study_group_service.dto.chat;

import lombok.*;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long id;
    private UUID uuid;
    private String name;
    private Long studyGroupId;
}
