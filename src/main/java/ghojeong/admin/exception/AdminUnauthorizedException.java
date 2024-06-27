package ghojeong.admin.exception;

import ghojeong.common.exception.UnauthorizedException;

public class AdminUnauthorizedException extends UnauthorizedException {
    public AdminUnauthorizedException(String message) {
        super(message);
    }
}
