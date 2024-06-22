package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.MakCardDTO;
import com.psychology.product.repository.model.Image;
import com.psychology.product.repository.model.MakCard;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MakCardMapper {
    MakCardDTO toDTO(MakCard makCard);

    default List<String> map(List<Image> value) {
        return value.stream()
                .map(Image::getName)
                .collect(Collectors.toList());
    }
}
