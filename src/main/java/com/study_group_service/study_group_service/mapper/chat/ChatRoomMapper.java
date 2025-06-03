package com.study_group_service.study_group_service.mapper.chat;

import com.study_group_service.study_group_service.dto.chat.ChatRoomDTO;
import com.study_group_service.study_group_service.entity.chat.ChatRoom;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatRoomMapper {

    public ChatRoomDTO toDto(ChatRoom chatRoom) {
        return ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .studyGroupId(chatRoom.getStudyRoom().getId())
                .build();
    }

    public List<ChatRoomDTO> chatRoomFromDto(List<ChatRoom> chatRooms) {
        return chatRooms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
