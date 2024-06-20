package com.psychology.product.repository.dto;

import com.psychology.product.repository.model.Image;
import com.psychology.product.repository.model.User;

import java.util.List;
import java.util.UUID;

public record MakCardDTO(
        UUID makCardId,
        String name,
        String description,
        Image previewImage,
        List<Image> images,
        User user
) {}