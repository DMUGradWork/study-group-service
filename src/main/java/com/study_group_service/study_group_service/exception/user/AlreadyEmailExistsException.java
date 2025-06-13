package com.study_group_service.study_group_service.exception.user;

public class AlreadyEmailExistsException extends RuntimeException {
    public AlreadyEmailExistsException(String message) {
        super(message);
    }
}
