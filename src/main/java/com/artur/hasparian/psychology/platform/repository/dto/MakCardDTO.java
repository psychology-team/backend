package com.artur.hasparian.psychology.platform.repository.dto;

import com.artur.hasparian.psychology.platform.repository.model.Image;
import com.artur.hasparian.psychology.platform.repository.model.User;

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