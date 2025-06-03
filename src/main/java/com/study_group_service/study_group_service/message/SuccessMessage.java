package com.study_group_service.study_group_service.message;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SuccessMessage {

    // 웹 만들시 반환 메세지 -> 아직 삽입 안함 (만들어 놓기만)
    private static final String delSuccessUser = "회원 삭제 완료";
    private static final String changeSuccessRole = "관리자 변경 완료";

    public String showDelSuccessUser() {
        return delSuccessUser;
    }
    public String showChangeSuccessRole() {
        return changeSuccessRole;
    }
}
