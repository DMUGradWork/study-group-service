package com.study_group_service.study_group_service.repository.study.room;

import com.study_group_service.study_group_service.entity.study.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudyRoomJpaRepository extends JpaRepository<StudyRoom, Long> {
    Optional<StudyRoom> findByName(String name);
    Optional<StudyRoom> findByChatRoomId(Long chatRoomId);
    void deleteById(StudyRoom studyRoom);
}
