package ghojeong.user.dto.response;

import ghojeong.common.dto.response.CommonResponse;
import ghojeong.user.domain.entity.UserWithTags;
import ghojeong.user.dto.response.data.UserWithTagsResponse;
import org.springframework.http.HttpStatus;

public final class UpdateUserResponse extends CommonResponse<UserWithTagsResponse> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.OK;
    private static final String MESSAGE = "Update My Info Success!";

    private UpdateUserResponse(UserWithTagsResponse data) {
        super(HTTP_STATUS, MESSAGE, data);
    }

    public static UpdateUserResponse of(UserWithTags user) {
        return new UpdateUserResponse(UserWithTagsResponse.of(user));
    }
}
