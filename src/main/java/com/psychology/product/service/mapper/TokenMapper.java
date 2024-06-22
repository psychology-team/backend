package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.TokenDTO;
import com.psychology.product.repository.model.Token;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {UserMapper.class})
public interface TokenMapper {
    TokenDTO toDTO(Token token);

    Token toDAO(TokenDTO token);
}
