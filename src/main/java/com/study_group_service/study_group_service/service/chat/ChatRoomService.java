package com.study_group_service.study_group_service.service.chat;

import com.study_group_service.study_group_service.dto.chat.ChatRoomDTO;
import java.util.List;

public interface ChatRoomService {
    List<ChatRoomDTO> getChatRooms();
}
