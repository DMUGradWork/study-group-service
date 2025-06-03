package com.study_group_service.study_group_service.dto.chat;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ChatRoomDTO {
    private Long id;
    private String name;
    private Long studyGroupId;
}
