package ghojeong.admin.dto.response;

import ghojeong.admin.dto.response.data.AdminLoginDto;
import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class AdminLoginResponse extends CommonResponse<AdminLoginDto> {
    public static final HttpStatus HTTP_STATUS = HttpStatus.CREATED;
    public static final String MESSAGE = "Admin Login Success!";

    public AdminLoginResponse(AdminLoginDto data) {
        super(HTTP_STATUS, MESSAGE, data);
    }
}
