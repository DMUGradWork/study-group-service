package com.study_group_service.study_group_service.repository;

import com.study_group_service.study_group_service.entity.chat.ChatRoomMessages;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatMessageJpaRepository extends JpaRepository<ChatRoomMessages, Long> {

}
