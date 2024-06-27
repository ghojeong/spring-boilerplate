package ghojeong.image.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadError {
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;

    @Override
    public String toString() {
        return "{ code=" + code + ", message='" + message + "' }";
    }
}
