package ghojeong.auth.dto.response;

import ghojeong.auth.dto.response.data.MemberLoginDto;
import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class MemberLoginResponse extends CommonResponse<MemberLoginDto> {
    public static final HttpStatus HTTP_STATUS = HttpStatus.CREATED;
    public static final String MESSAGE = "Create Access Token Success!";

    public MemberLoginResponse(MemberLoginDto data) {
        super(HTTP_STATUS, MESSAGE, data);
    }
}
