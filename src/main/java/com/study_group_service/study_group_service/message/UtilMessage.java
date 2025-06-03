package com.study_group_service.study_group_service.message;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class UtilMessage {

    // 유틸 클래스라 메서드를 static 처리함.
    // 에러 클래스는 혹시 몰라 메서드 static 처리를 안함.

    private static final String ROOM_ID = "roomId";
    private static final String CHAT_ID = "chatId";
    private static final String SEND = "수신 : {}";

    public static String showRoomMessage() { return ROOM_ID;}
    public static String showChatMessage() { return CHAT_ID;}
    public static String showSend() {return SEND;}
}
