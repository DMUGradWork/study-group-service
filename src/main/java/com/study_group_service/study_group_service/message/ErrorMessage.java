package com.study_group_service.study_group_service.message;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ErrorMessage {

    private static final String NO_USER_MESSAGE = "해당 회원이 없습니다.";
    private static final String NO_STUDY_ROOM_MESSAGE = "해당 스터디룸이 없습니다.";
    private static final String NO_CATEGORY_MESSAGE = "해당 카테고리가 없습니다.";
    private static final String NO_USER_EMAIL_MESSAGE = "해당 이메일의 사용자를 찾을 수 없습니다.";
    private static final String NO_CHAT_ROOM_MESSAGE = "해당 채팅방이 존재하지 않습니다.";
    private static final String NO_USER_THIS_CHAT_MESSAGE = "해당 유저는 이 스터디룸에 참가하고 있지 않습니다.";
    private static final String ALREADY_USER_IN_STUDY_MESSAGE = "이미 이 스터디방에 참여한 유저입니다.";
    private static final String SEND_MESSAGE_ERROR_MESSAGE = "메세지 수신 중 에러 발생 : {}";
    private static final String ALREADY_USE_EMAIL_MESSAGE = "이메일이 이미 존재합니다.";

    public static String showNoUserThisChatMessage() {return NO_USER_THIS_CHAT_MESSAGE;}
    public static String showNoUserMessage() {
        return NO_USER_MESSAGE;
    }
    public static String showNoUserEmailMessage() {
        return NO_USER_EMAIL_MESSAGE;
    }
    public static String showNoCategoryMessage() {return NO_CATEGORY_MESSAGE;}
    public static String showNoStudyRoomMessage() {return NO_STUDY_ROOM_MESSAGE;}
    public static String showNoChatRoomMessage() {return NO_CHAT_ROOM_MESSAGE;}
    public static String showAlreadyUserInStudyMessage() {return ALREADY_USER_IN_STUDY_MESSAGE;}
    public static String showSendMessageError() {return SEND_MESSAGE_ERROR_MESSAGE;}
    public static String showAlreadyUseEmailMessage() {return ALREADY_USE_EMAIL_MESSAGE;}
}
