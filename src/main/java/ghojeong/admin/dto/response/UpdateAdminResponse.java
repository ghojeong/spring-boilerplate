package ghojeong.admin.dto.response;

import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.admin.dto.response.data.AdminResponse;
import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class UpdateAdminResponse extends CommonResponse<AdminResponse> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.OK;
    private static final String MESSAGE = "Update Admin Info Success!";

    private UpdateAdminResponse(AdminResponse data) {
        super(HTTP_STATUS, MESSAGE, data);
    }

    public static UpdateAdminResponse of(AdminUser adminUser) {
        return new UpdateAdminResponse(AdminResponse.of(adminUser));
    }
}
