package ghojeong.auth.exception;

import ghojeong.auth.dto.request.MemberLoginRequest;
import ghojeong.common.exception.UnauthorizedException;

public class EmptyLoginRequestException extends UnauthorizedException {
    private static final String MESSAGE = "Empty Login Request: type(%s), ssoId(%s), password(%s)";

    public EmptyLoginRequestException(MemberLoginRequest memberLoginRequest) {
        super(String.format(
                MESSAGE,
                memberLoginRequest.type(),
                memberLoginRequest.ssoId(),
                memberLoginRequest.password()
        ));
    }
}
