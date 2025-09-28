package com.study_group_service.study_group_service.repository.study;

import com.study_group_service.study_group_service.entity.study.StudyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudyRoomJpaRepository extends JpaRepository<StudyRoom, Long> {
    Optional<StudyRoom> findByName(String name);
    Optional<StudyRoom> findByUuid(UUID uuid);
    Optional<StudyRoom> findByChatRoomId(Long chatRoomId);
    Optional<StudyRoom> findByChatRoomUuid(UUID chatRoomUuid);
}
