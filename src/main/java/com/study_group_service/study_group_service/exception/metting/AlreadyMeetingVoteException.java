package com.study_group_service.study_group_service.exception.metting;

import static com.study_group_service.study_group_service.message.ErrorMessage.showAlreadyUseMeetingMessage;

public class AlreadyMeetingVoteException extends RuntimeException {
    public AlreadyMeetingVoteException() {
        super(showAlreadyUseMeetingMessage());
    }
}
