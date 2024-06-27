package ghojeong.auth.dto.response;

import ghojeong.auth.dto.response.data.RefreshTokenDto;
import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class RefreshTokenResponse extends CommonResponse<RefreshTokenDto> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.CREATED;
    private static final String MESSAGE = "Refresh Token Success!";

    public RefreshTokenResponse(RefreshTokenDto data) {
        super(HTTP_STATUS, MESSAGE, data);
    }
}
