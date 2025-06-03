package com.study_group_service.study_group_service.mapper.study;

import com.study_group_service.study_group_service.dto.study.StudyRoomParticipantDTO;
import com.study_group_service.study_group_service.entity.study.StudyRoomParticipant;
import org.springframework.stereotype.Component;

@Component
public class StudyRoomParticipantMapper {

    public StudyRoomParticipantDTO toDto(StudyRoomParticipant participant) {
        return StudyRoomParticipantDTO.builder()
                .id(participant.getId())
                .userId(participant.getUser().getId())
                .studyRoomId(participant.getStudyRoom().getId())
                .build();
    }
}
