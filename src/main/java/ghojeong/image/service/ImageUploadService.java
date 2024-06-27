package ghojeong.image.service;

import ghojeong.image.dto.FetchUploadUrlResponse;
import ghojeong.image.repository.ImageUploadRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageUploadService {
    private final ImageUploadRepository imageUploadRepository;

    public FetchUploadUrlResponse fetchUploadUrl() {
        final String imageId = imageUploadRepository.getImageId();
        return new FetchUploadUrlResponse(
                imageUploadRepository.getUploadUrl(imageId),
                imageUploadRepository.getImageUrl(imageId)
        );
    }
}
