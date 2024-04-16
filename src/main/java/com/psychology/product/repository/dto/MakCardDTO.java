package com.psychology.product.repository.dto;

import com.psychology.product.repository.model.ImageDAO;
import com.psychology.product.repository.model.UserDAO;

import java.util.List;
import java.util.UUID;

public record MakCardDTO(
        UUID makCardId,
        String name,
        String description,
        ImageDAO previewImage,
        List<ImageDAO> images,
        UserDAO user
) {}