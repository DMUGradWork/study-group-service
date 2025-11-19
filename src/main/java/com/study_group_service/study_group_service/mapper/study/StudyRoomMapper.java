package com.study_group_service.study_group_service.mapper.study;

import com.study_group_service.study_group_service.dto.study.StudyRoomDTO;
import com.study_group_service.study_group_service.entity.study.StudyRoomCategory;
import com.study_group_service.study_group_service.entity.study.StudyRoom;
import com.study_group_service.study_group_service.entity.study.StudyRoomParticipant;
import com.study_group_service.study_group_service.entity.user.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudyRoomMapper {
    public StudyRoomDTO toDto(StudyRoom studyRoom) {
        return StudyRoomDTO.builder()
                .id(studyRoom.getId())
                .name(studyRoom.getName())
                .studyRoomHostId(studyRoom.getUser().getId())
                .categoriesId(studyRoom.getStudyRoomCategory().getId())
                .chatId(studyRoom.getChatRoom().getId())
                .createdAt(studyRoom.getCreatedAt())
                .peopleCount(studyRoom.getPeopleCount())
                .rules(studyRoom.getRules())
                .notification(studyRoom.getNotification())
                .build();
    }

    public StudyRoom toEntity(StudyRoomDTO studyRoomDTO, Users user, StudyRoomCategory category) {
        return StudyRoom.builder()
                .name(studyRoomDTO.getName())
                .peopleCount(studyRoomDTO.getPeopleCount())
                .rules(studyRoomDTO.getRules())
                .notification(studyRoomDTO.getNotification())
                .createdAt(LocalDateTime.now())
                .user(user)
                .studyRoomCategory(category)
                .build();
    }

    public List<StudyRoomDTO> toDto(List<StudyRoomParticipant> participants) {
        return participants.stream()
                .map(participant -> toDto(participant.getStudyRoom()))
                .toList();
    }

    // 조회 리턴 값 중복 분리
    public List<StudyRoomDTO> studyRoomFromDto(List<StudyRoom> studyRooms) {
        return studyRooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
