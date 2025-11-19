package User.User.message;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SuccessMessage {
    private static final String delSuccessUser = "회원 삭제 완료";
    private static final String changeSuccessRole = "관리자 변경 완료";

    public String showDelSuccessUser() {
        return delSuccessUser;
    }

    public String showChangeSuccessRole() {
        return changeSuccessRole;
    }
}
