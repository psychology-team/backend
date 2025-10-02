package com.artur.hasparian.psychology.platform.service.mapper;

import com.artur.hasparian.psychology.platform.repository.dto.ImageDTO;
import com.artur.hasparian.psychology.platform.repository.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {
    abstract ImageDTO toDTO(Image image);

    public Image toDAO(String string) {
        return null;
    }
}