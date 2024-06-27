package ghojeong.image.exception;

import ghojeong.common.exception.UnavailableException;
import ghojeong.image.domain.UploadError;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;

public class CloudFlareException extends UnavailableException {
    public CloudFlareException(
            List<UploadError> errors,
            List<String> messages
    ) {
        super(Stream.concat(
                errors.stream().map(UploadError::toString),
                messages.stream()
        ).collect(Collectors.joining(lineSeparator())));
    }

    public CloudFlareException(String message) {
        super(message);
    }
}
