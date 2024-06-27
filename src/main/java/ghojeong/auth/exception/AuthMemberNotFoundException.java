package ghojeong.auth.exception;

import ghojeong.common.exception.ForbiddenException;

public class AuthMemberNotFoundException extends ForbiddenException {
    private static final String MESSAGE = "Auth Member Not Found: %s";

    public AuthMemberNotFoundException(String identifier) {
        super(String.format(MESSAGE, identifier));
    }
}
