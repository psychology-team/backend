package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.ImageDTO;
import com.psychology.product.repository.model.ImageDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {
    abstract ImageDTO toDTO(ImageDAO imageDAO);

    public ImageDAO toDAO(String string) {
        return null;
    }
}