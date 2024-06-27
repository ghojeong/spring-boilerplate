package ghojeong.user.dto.response;

import ghojeong.common.dto.response.CommonResponse;
import ghojeong.user.domain.entity.User;
import ghojeong.user.dto.response.data.UserSeqResponse;
import org.springframework.http.HttpStatus;

public final class DeleteUserResponse extends CommonResponse<UserSeqResponse> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.OK;
    private static final String MESSAGE = "Delete My Account Success!";

    private DeleteUserResponse(UserSeqResponse data) {
        super(HTTP_STATUS, MESSAGE, data);
    }

    public static DeleteUserResponse of(User user) {
        return new DeleteUserResponse(UserSeqResponse.of(user));
    }
}
