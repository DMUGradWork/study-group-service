package com.study_group_service.study_group_service.exception.chat;

public class ChatMessageSendException extends RuntimeException {
  public ChatMessageSendException(String message) {
    super(message);
  }
}
