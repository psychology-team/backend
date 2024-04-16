package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.MakCardDTO;
import com.psychology.product.repository.model.ImageDAO;
import com.psychology.product.repository.model.MakCardDAO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MakCardMapper {
    MakCardDTO toDTO(MakCardDAO makCardDAO);

    default List<String> map(List<ImageDAO> value) {
        return value.stream()
                .map(ImageDAO::getName)
                .collect(Collectors.toList());
    }
}
