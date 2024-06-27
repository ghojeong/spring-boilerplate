package ghojeong.admin.exception;

import ghojeong.common.exception.UnauthorizedException;

public class AdminLoginFailedException extends UnauthorizedException {
    private static final String MESSAGE = "Invalid Email or Password";

    public AdminLoginFailedException() {
        super(MESSAGE);
    }
}
