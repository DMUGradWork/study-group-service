package com.study_group_service.study_group_service.dto.study;

import lombok.*;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudyRoomParticipantDTO {
    private Long id;
    private UUID uuid;
    private Long userId;
    private Long studyRoomId;
}
