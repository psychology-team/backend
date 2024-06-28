package com.psychology.product.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MacCardService {
    /**
     * Creates a new MAC card using the provided files.
     *
     * @param files a list of multipart files to be used in the creation of the MAC card
     */
    void createCard(List<String> imageLinks);
}
