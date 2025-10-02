package com.artur.hasparian.psychology.platform.service.mapper;

import com.artur.hasparian.psychology.platform.repository.dto.MakCardDTO;
import com.artur.hasparian.psychology.platform.repository.model.Image;
import com.artur.hasparian.psychology.platform.repository.model.MakCard;
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
