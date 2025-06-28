package com.study_group_service.study_group_service.exception.chat;

import static com.study_group_service.study_group_service.message.ErrorMessage.showSendMessageError;

public class ChatMessageSendException extends RuntimeException {
    public ChatMessageSendException() {
        super(showSendMessageError());
    }
}
