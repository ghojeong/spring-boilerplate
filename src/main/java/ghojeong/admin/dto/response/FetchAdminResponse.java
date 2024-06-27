package ghojeong.admin.dto.response;

import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.admin.dto.response.data.AdminResponse;
import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class FetchAdminResponse extends CommonResponse<AdminResponse> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.OK;
    private static final String MESSAGE = "Fetch Admin Info Success!";

    private FetchAdminResponse(AdminResponse data) {
        super(HTTP_STATUS, MESSAGE, data);
    }

    public static FetchAdminResponse of(AdminUser adminUser) {
        return new FetchAdminResponse(AdminResponse.of(adminUser));
    }
}
