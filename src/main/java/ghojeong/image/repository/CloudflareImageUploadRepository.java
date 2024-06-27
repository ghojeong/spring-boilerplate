package ghojeong.image.repository;

import ghojeong.image.domain.UploadUrl;
import ghojeong.image.exception.CloudFlareException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Repository
@Profile("!test")
public class CloudflareImageUploadRepository implements ImageUploadRepository {
    private static final String IMAGE_DELIVERY_HASH = "1rkkarLgz_K-5HM9rg4ijw";
    private static final String UPLOAD_URL = "https://upload.imagedelivery.net/"
            + IMAGE_DELIVERY_HASH + "/%s";
    private static final String IMAGE_URL = "https://imagedelivery.net/"
            + IMAGE_DELIVERY_HASH + "/%s/public";

    private static final String ACCOUNT_ID = "c515c3386081ce99eb9b726f93b90ced";
    private static final String CLOUDFLARE_API_TOKEN = "Bearer WUCVQXK0XALoov5y8YSkoIFsGTsqP9Xs-jPkwano";
    private static final String CLOUDFLARE_URL = "https://api.cloudflare.com/client/v4/accounts/" +
            ACCOUNT_ID + "/images/v2/direct_upload";
    private static final WebClient webClient = WebClient.builder()
            .baseUrl(CLOUDFLARE_URL)
            .defaultHeader(AUTHORIZATION, CLOUDFLARE_API_TOKEN)
            .defaultHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .build();

    @Override
    public String getImageId() {
        return retrieve().getImageId();
    }

    @Override
    public String getUploadUrl(String imageId) {
        return getCloudflareUploadUrl(imageId);
    }

    @Override
    public String getImageUrl(String imageId) {
        return String.format(IMAGE_URL, imageId);
    }

    private UploadUrl retrieve() {
        try {
            return Optional.ofNullable(
                    webClient.post().retrieve()
                            .bodyToMono(UploadUrl.class)
                            .block()
            ).orElseThrow(() -> new CloudFlareException("UploadUrl Object is NULL"));
        } catch (WebClientResponseException e) {
            throw new CloudFlareException(e.getResponseBodyAsString());
        }
    }

    private String getCloudflareUploadUrl(String imageId) {
        return String.format(UPLOAD_URL, imageId);
    }
}
