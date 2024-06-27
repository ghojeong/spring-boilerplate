package ghojeong.auth.dto.response;

import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class LogoutResponse extends CommonResponse<LogoutResponse.LogoutDto> {
    public static final HttpStatus HTTP_STATUS = HttpStatus.OK;
    public static final String MESSAGE = "Logout Success!";

    public LogoutResponse(Long userSeq) {
        super(HTTP_STATUS, MESSAGE, new LogoutDto(userSeq));
    }

    protected record LogoutDto(
            Long userSeq
    ) {}
}
