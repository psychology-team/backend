package com.psychology.product.service;

import com.psychology.product.repository.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    /**
     * Creates an Image entity from a multipart file.
     *
     * @param multipartFile The MultipartFile representing the image file.
     * @return The created Image entity.
     */
    Image createImage(MultipartFile multipartFile);

}
