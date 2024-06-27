package ghojeong.admin.dto.response;

import ghojeong.admin.domain.entity.AdminUser;
import ghojeong.admin.dto.response.data.AdminResponse;
import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class CreateAdminResponse extends CommonResponse<AdminResponse> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.CREATED;
    private static final String MESSAGE = "Create Admin Success!";

    private CreateAdminResponse(AdminResponse data) {
        super(HTTP_STATUS, MESSAGE, data);
    }

    public static CreateAdminResponse of(AdminUser adminUser) {
        return new CreateAdminResponse(AdminResponse.of(adminUser));
    }
}
