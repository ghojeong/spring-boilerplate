package ghojeong.user.exception;

import ghojeong.common.exception.BadRequestException;

public class InvalidUserException extends BadRequestException {
    private static final String MESSAGE = "Registration failed. You must have a working email address set up for your account.";

    public InvalidUserException() {
        super(MESSAGE);
    }
}
