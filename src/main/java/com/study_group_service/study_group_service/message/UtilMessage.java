package com.study_group_service.study_group_service.message;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UtilMessage {

    private static final String ROOM_ID = "roomId";
    private static final String CHAT_ID = "chatId";
    private static final String SEND = "수신 : {}";

    public static String showRoomMessage() { return ROOM_ID;}
    public static String showChatMessage() { return CHAT_ID;}
    public static String showSend() {return SEND;}
}
