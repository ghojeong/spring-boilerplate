package ghojeong.admin.dto.response;

import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class DeleteAdminResponse extends CommonResponse<String> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.OK;
    private static final String MESSAGE = "Delete Admin Success!";

    private DeleteAdminResponse(String data) {
        super(HTTP_STATUS, MESSAGE, data);
    }

    public static DeleteAdminResponse of(String email) {
        return new DeleteAdminResponse(email);
    }
}
