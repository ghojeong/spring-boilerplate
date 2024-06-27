package ghojeong.auth.exception;

import ghojeong.common.exception.ForbiddenException;

public class InvalidTokenException extends ForbiddenException {

    private static final String MESSAGE = "Invalid Token: %s";

    public InvalidTokenException(String token) {
        super(String.format(MESSAGE, token));
    }
}
