package ghojeong.auth.exception;

import ghojeong.common.exception.UnauthorizedException;

public class MemberLogInFailedException extends UnauthorizedException {
    private static final String MESSAGE = "회원가입이 되어 있지 않은 유저입니다.";

    public MemberLogInFailedException() {
        super(MESSAGE);
    }
}
