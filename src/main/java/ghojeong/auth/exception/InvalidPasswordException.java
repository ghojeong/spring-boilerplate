package ghojeong.auth.exception;

import ghojeong.common.exception.UnauthorizedException;

public class InvalidPasswordException extends UnauthorizedException {
    private static final String MESSAGE = "Invalid Password: %s";

    public InvalidPasswordException(String authPassword) {
        super(String.format(MESSAGE, authPassword));
    }
}
