package com.study_group_service.study_group_service.exception.study;

import static com.study_group_service.study_group_service.message.ErrorMessage.showNoStudyRoomMessage;

public class StudyRoomNotFoundException extends RuntimeException {

    public StudyRoomNotFoundException() {
        super(showNoStudyRoomMessage());
    }
}
