package ghojeong.image.controller;

import ghojeong.image.dto.FetchUploadUrlResponse;
import ghojeong.image.service.ImageUploadService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageController {
    private final ImageUploadService imageUploadService;

    @Secured({"ROLE_MEMBER", "ROLE_ADMIN"})
    @GetMapping("/images/upload-url")
    public FetchUploadUrlResponse fetchImageUploadUrl() {
        return imageUploadService.fetchUploadUrl();
    }
}
