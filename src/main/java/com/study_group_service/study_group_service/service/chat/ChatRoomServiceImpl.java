package com.study_group_service.study_group_service.service.chat;

import com.study_group_service.study_group_service.dto.chat.ChatRoomDTO;
import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import com.study_group_service.study_group_service.exception.chat.ChatRoomNotFoundException;
import com.study_group_service.study_group_service.mapper.chat.ChatRoomMapper;
import com.study_group_service.study_group_service.message.ErrorMessage;
import com.study_group_service.study_group_service.repository.chat.ChatRoomJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomJpaRepository chatRoomJpaRepository;
    private final ErrorMessage errorMessage;
    private final ChatRoomMapper chatRoomMapper;

    // 모든 채팅방 조회
    @Override
    @Transactional
    public List<ChatRoomDTO> getChatRooms() {
        List<ChatRoom> chatRooms = chatRoomJpaRepository.findAll();

        if(chatRooms.isEmpty()) {
            throw new ChatRoomNotFoundException(errorMessage.showNoChatRoomMessage());
        }

        return chatRoomMapper.chatRoomFromDto(chatRooms);
    }

}
