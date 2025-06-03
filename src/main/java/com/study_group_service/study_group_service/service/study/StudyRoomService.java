package com.study_group_service.study_group_service.service.study;

import com.study_group_service.study_group_service.dto.study.StudyRoomDTO;
import com.study_group_service.study_group_service.dto.study.StudyRoomParticipantDTO;

import java.util.List;

public interface StudyRoomService {
    List<StudyRoomDTO> getStudyRooms();
    StudyRoomDTO getStudyRoom(String name);
    StudyRoomDTO setStudyRoom(StudyRoomDTO studyRoomDTO);
    void deleteStudyRoom(Long id);
    StudyRoomDTO setStudyRoomRules(StudyRoomDTO studyRoomDTO);
    StudyRoomDTO setStudyRoomNotification(StudyRoomDTO studyRoomDTO);
    void deleteRules(Long id);
    void deleteNotification(Long id);
    void deleteStudyRoomParticipants(Long studyRoomId, Long userId);
    void joinStudyRoom(StudyRoomParticipantDTO dto);
    List<StudyRoomDTO> findStudyRoomsByUser(Long userId);
    List<StudyRoomParticipantDTO> findAllByStudyRoomId(Long studyRoomId);
    StudyRoomDTO findByChatRoomId(Long studyRoomId);
}
