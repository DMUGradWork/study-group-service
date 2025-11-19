package com.study_group_service.study_group_service.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyRoomParticipantDTO {
    private Long id;
    private Long userId;
    private Long studyRoomId;
}
