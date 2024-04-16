package com.psychology.product.service.impl;

import com.psychology.product.repository.MacCardRepository;
import com.psychology.product.repository.dto.MakCardDTO;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.ImageDAO;
import com.psychology.product.repository.model.MakCardDAO;
import com.psychology.product.repository.model.UserDAO;
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
    public MakCardDAO createCard(List<MultipartFile> files) {
        UserDTO user = userService.getCurrentUser();
        UserDAO userDAO = userMapper.toDAO(user);
        MakCardDAO card = new MakCardDAO();
        card.setUser(userDAO);
        List<ImageDAO> images = new ArrayList<>();
        for (MultipartFile file : files) {
            images.add(imageService.createImage(file));
        }
        card.setImages(images);
        card.setPreviewImage(images.get(0));
        return macCardRepository.save(card);
    }
}

