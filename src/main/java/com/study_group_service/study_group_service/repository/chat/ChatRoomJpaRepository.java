package com.study_group_service.study_group_service.repository;

import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {
}
