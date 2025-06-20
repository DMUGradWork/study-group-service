package com.study_group_service.study_group_service.exception.chat;

import static com.study_group_service.study_group_service.message.ErrorMessage.showNoChatRoomMessage;

public class ChatRoomNotFoundException extends RuntimeException {
    public ChatRoomNotFoundException() {
        super(showNoChatRoomMessage());
    }
}
