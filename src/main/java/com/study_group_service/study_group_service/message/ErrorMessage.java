package User.User.message;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ErrorMessage {

    private static final String NO_USER_MESSAGE = "회원이 없습니다.";
    private static final String NO_USER_EMAIL_MESSAGE = "해당 이메일의 사용자를 찾을 수 없습니다.";

    public String showNoUserMessage() {
        return NO_USER_MESSAGE;
    }
    public String showNoUserEmailMessage() {
        return NO_USER_EMAIL_MESSAGE;
    }
}
