package com.psychology.product.service.impl;

import com.psychology.product.repository.ImageRepository;
import com.psychology.product.repository.model.Image;
import com.psychology.product.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Cacheable(cacheNames = "images")
    @Override
    public Image createImage(String imageLink) {
        Image image = new Image();
        image.setContent(imageLink);
        return imageRepository.save(image);
    }
}
