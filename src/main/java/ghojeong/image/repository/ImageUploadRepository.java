package ghojeong.image.repository;

public interface ImageUploadRepository {
    String getImageId();

    String getUploadUrl(String imageId);

    String getImageUrl(String imageId);
}
