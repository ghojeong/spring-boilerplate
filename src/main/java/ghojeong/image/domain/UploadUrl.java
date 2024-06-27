package ghojeong.image.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ghojeong.image.exception.CloudFlareException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.util.Collections.emptyList;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadUrl {
    @JsonProperty("success")
    private boolean success;
    @JsonProperty("result")
    private Result result;
    @JsonProperty("errors")
    private List<UploadError> errors;
    @JsonProperty("messages")
    private List<String> messages;

    public static UploadUrl of(String id, String uploadURL) {
        return new UploadUrl(
                true,
                new Result(id, uploadURL),
                emptyList(),
                emptyList()
        );
    }

    @JsonIgnore
    public String getImageId() {
        validate();
        return result.id;
    }

    @JsonIgnore
    public String getUploadUrl() {
        validate();
        return result.uploadURL;
    }

    private void validate() {
        if (!success || result == null
                || result.id == null
                || result.uploadURL == null) {
            throw new CloudFlareException(errors, messages);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    private static class Result {
        @JsonProperty("id")
        private String id;
        @JsonProperty("uploadURL")
        private String uploadURL;
    }
}
