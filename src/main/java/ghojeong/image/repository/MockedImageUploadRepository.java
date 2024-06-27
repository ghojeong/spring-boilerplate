package ghojeong.image.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class MockedImageUploadRepository implements ImageUploadRepository {
    private static final String IMAGE_ID = "a499caa5-a491-4480-58d5-735e2bb08400";
    private static final String IMAGE_URL = "https://imagedelivery.net/1rkkarLgz_K-5HM9rg4ijw/";

    @Override
    public String getImageId() {
        return IMAGE_ID;
    }

    @Override
    public String getUploadUrl(String imageId) {
        return String.format("https://mocked-upload-url/v1/images/upload/%s", imageId);
    }

    @Override
    public String getImageUrl(String imageId) {
        return String.format("%s/%s/public", IMAGE_URL, imageId);
    }
}
