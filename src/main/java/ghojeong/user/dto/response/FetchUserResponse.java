package ghojeong.user.dto.response;

import ghojeong.common.dto.response.CommonResponse;
import ghojeong.user.domain.entity.UserWithTags;
import ghojeong.user.dto.response.data.UserWithTagsResponse;
import org.springframework.http.HttpStatus;

public final class FetchUserResponse extends CommonResponse<UserWithTagsResponse> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.OK;
    private static final String MESSAGE = "Fetch User Info Success!";

    private FetchUserResponse(UserWithTagsResponse data) {
        super(HTTP_STATUS, MESSAGE, data);
    }

    public static FetchUserResponse of(UserWithTags user) {
        return new FetchUserResponse(UserWithTagsResponse.of(user));
    }
}
