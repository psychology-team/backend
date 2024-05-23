package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.TokenDTO;
import com.psychology.product.repository.model.TokenDAO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {UserMapper.class})
public interface TokenMapper {
    TokenDTO toDTO(TokenDAO token);

    TokenDAO toDAO(TokenDTO token);
}
