package com.psychology.product.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.psychology.product.repository.ImageRepository;
import com.psychology.product.repository.model.ImageDAO;
import com.psychology.product.service.ImageService;
import com.psychology.product.util.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final Cache<UUID, ImageDAO> imageCache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    @Override
    public ImageDAO createImage(MultipartFile multipartFile) {
        ImageDAO image = new ImageDAO();
        image.setName(multipartFile.getName());
        image.setContentType(multipartFile.getContentType());
        image.setSize(multipartFile.getSize());
        try {
            image.setContent(multipartFile.getBytes());
        } catch (IOException e) {
            throw new BadRequestException("Could not read image file");
        }
        image.setCreatedTimestamp(Instant.now());
        ImageDAO savedImage = imageRepository.save(image);
        imageCache.put(savedImage.getId(), savedImage);
        return savedImage;
    }

    @Override
    public Optional<ImageDAO> getImageById(UUID id) {
        ImageDAO cachedImage = imageCache.getIfPresent(id);
        if (cachedImage != null) {
            return Optional.of(cachedImage);
        }
        return imageRepository.findById(id)
                .map(image -> {
                    imageCache.put(id, image);
                    return image;
                });
    }
}
