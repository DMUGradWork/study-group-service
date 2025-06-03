package com.study_group_service.study_group_service.repository.chat;

import com.study_group_service.study_group_service.entity.chat.ChatRoomMessages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageJpaRepository extends JpaRepository<ChatRoomMessages, Long> {
    List<ChatRoomMessages> findAllByChatRoomIdOrderBySentAtAsc(Long chatRoomId);

}
