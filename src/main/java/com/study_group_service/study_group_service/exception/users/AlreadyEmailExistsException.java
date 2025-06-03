package com.study_group_service.study_group_service.exception.users;

public class AlreadyEmailExistsException extends RuntimeException {
    public AlreadyEmailExistsException(String message) {
        super(message);
    }
}
