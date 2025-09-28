package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.dto.study.StudyRoomDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomParticipantDTO;

import java.util.List;
import java.util.UUID;

public interface StudyRoomService {
    List<StudyRoomDTO> getStudyRooms();
    StudyRoomDTO getStudyRoom(String name);
    StudyRoomDTO getStudyRoomByUuid(UUID uuid);
    StudyRoomDTO setStudyRoom(StudyRoomDTO studyRoomDTO);
    void deleteStudyRoom(Long id);
    void deleteStudyRoomByUuid(UUID uuid);
    StudyRoomDTO setStudyRoomRules(StudyRoomDTO studyRoomDTO);
    StudyRoomDTO setStudyRoomNotification(StudyRoomDTO studyRoomDTO);
    void deleteRules(Long id);
    void deleteRulesByUuid(UUID uuid);
    void deleteNotification(Long id);
    void deleteNotificationByUuid(UUID uuid);
    void deleteStudyRoomParticipants(Long studyRoomId, Long userId);
    void deleteStudyRoomParticipantsByUuid(UUID studyRoomUuid, UUID userUuid);
    void joinStudyRoom(StudyRoomParticipantDTO dto);
    List<StudyRoomDTO> findStudyRoomsByUser(Long userId);
    List<StudyRoomDTO> findStudyRoomsByUserUuid(UUID userUuid);
    List<StudyRoomParticipantDTO> findAllByStudyRoomId(Long studyRoomId);
    List<StudyRoomParticipantDTO> findAllByStudyRoomUuid(UUID studyRoomUuid);
    StudyRoomDTO findByChatRoomId(Long studyRoomId);
    StudyRoomDTO findByChatRoomUuid(UUID studyRoomUuid);
}
