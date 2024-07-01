package com.psychology.product.service.impl;

import com.psychology.product.repository.MacCardRepository;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.Image;
import com.psychology.product.repository.model.MakCard;
import com.psychology.product.repository.model.User;
import com.psychology.product.service.ImageService;
import com.psychology.product.service.MacCardService;
import com.psychology.product.service.UserService;
import com.psychology.product.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MacCardServiceImpl implements MacCardService {
    private final MacCardRepository macCardRepository;
    private final ImageService imageService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public void createCard(List<String> imageLinks) {
        UserDTO user = userService.getCurrentUser();
        User userDAO = userMapper.toDAO(user);
        MakCard card = new MakCard();
        card.setUser(userDAO);
        List<Image> images = new ArrayList<>();
        for (String links : imageLinks) {
            images.add(imageService.createImage(links));
        }
        card.setImages(images);
        card.setPreviewImage(images.get(0));
        macCardRepository.save(card);
    }
}

