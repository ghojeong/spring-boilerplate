package ghojeong.image.dto;

import ghojeong.common.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;

public final class FetchUploadUrlResponse extends CommonResponse<FetchUploadUrlResponse.UploadUrlResponse> {
    private static final HttpStatus HTTP_STATUS = HttpStatus.OK;
    private static final String MESSAGE = "Fetch Image Upload URL Success!";

    public FetchUploadUrlResponse(String imageUploadUrl, String imageUrl) {
        super(HTTP_STATUS, MESSAGE, new UploadUrlResponse(
                imageUploadUrl,
                imageUrl
        ));
    }

    record UploadUrlResponse(
            String imageUploadUrl,
            String imageUrl
    ) {}
}
