package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.ImageDTO;
import com.psychology.product.repository.model.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {
    abstract ImageDTO toDTO(Image image);

    public Image toDAO(String string) {
        return null;
    }
}