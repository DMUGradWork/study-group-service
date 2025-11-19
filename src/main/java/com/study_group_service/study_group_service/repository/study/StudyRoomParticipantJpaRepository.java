package com.study_group_service.study_group_service.repository.study;

import com.study_group_service.study_group_service.entity.study.StudyRoomParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface StudyRoomParticipantJpaRepository extends JpaRepository<StudyRoomParticipant, Long> {
    Optional<StudyRoomParticipant> findByUserIdAndStudyRoomId(Long userId, Long studyRoomId);
    List<StudyRoomParticipant> findAllByUserId(Long userId);
    List<StudyRoomParticipant> findAllByStudyRoomId(Long studyRoomId);
    boolean existsByUserIdAndStudyRoomId(Long userId, Long studyRoomId);
}
