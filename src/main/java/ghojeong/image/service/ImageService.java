package ghojeong.image.service;


import ghojeong.image.domain.Image;
import ghojeong.image.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageService {
    private final ImageRepository imageRepository;

    public void update(String oldImage, String newImage) {
        if (notBlank(oldImage)) {
            imageRepository.save(Image.oldImage(oldImage));
        }
        if (notBlank(newImage)) {
            imageRepository.save(Image.newImage(newImage));
        }
    }

    public void update(List<String> oldImages, List<String> newImages) {
        imageRepository.saveAll(
                oldImages.stream().filter(this::notBlank)
                        .map(Image::oldImage)
                        .toList()
        );
        imageRepository.saveAll(
                newImages.stream().filter(this::notBlank)
                        .map(Image::oldImage)
                        .toList()
        );
    }

    private boolean notBlank(String image) {
        return image != null && !image.isBlank();
    }
}
