package com.psychology.product.service.impl;

import com.psychology.product.repository.ImageRepository;
import com.psychology.product.repository.model.Image;
import com.psychology.product.service.ImageService;
import com.psychology.product.util.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image createImage(MultipartFile multipartFile) {
        Image image = new Image();
        image.setName(multipartFile.getName());
        image.setContentType(multipartFile.getContentType());
        image.setSize(multipartFile.getSize());
        try {
            image.setContent(multipartFile.getBytes());
        } catch (IOException e) {
            throw new BadRequestException("Could not read image file");
        }
        image.setCreatedTimestamp(Instant.now());
        return imageRepository.save(image);
    }
}
