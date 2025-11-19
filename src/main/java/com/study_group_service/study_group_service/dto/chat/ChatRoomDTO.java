package com.study_group_service.study_group_service.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ChatRoomDTO {
    private Long id;
    private Long studyGroupId;
    private String name;
}
