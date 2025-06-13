package com.study_group_service.study_group_service.exception.user;

public class AlreadyUserException extends RuntimeException {
    public AlreadyUserException(String message) {
        super(message);
    }
}
