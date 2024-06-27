package ghojeong.auth.exception;

import ghojeong.common.exception.ForbiddenException;

public class AuthAdminNotFoundException extends ForbiddenException {
    private static final String MESSAGE = "Auth Admin Not Found: %s";

    public AuthAdminNotFoundException(String email) {
        super(String.format(MESSAGE, email));
    }
}
