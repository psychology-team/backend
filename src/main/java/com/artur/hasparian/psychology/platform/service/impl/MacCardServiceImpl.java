package com.artur.hasparian.psychology.platform.service.impl;

import com.artur.hasparian.psychology.platform.repository.MacCardRepository;
import com.artur.hasparian.psychology.platform.repository.dto.UserDTO;
import com.artur.hasparian.psychology.platform.repository.model.Image;
import com.artur.hasparian.psychology.platform.repository.model.MakCard;
import com.artur.hasparian.psychology.platform.repository.model.User;
import com.artur.hasparian.psychology.platform.service.ImageService;
import com.artur.hasparian.psychology.platform.service.MacCardService;
import com.artur.hasparian.psychology.platform.service.UserService;
import com.artur.hasparian.psychology.platform.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

