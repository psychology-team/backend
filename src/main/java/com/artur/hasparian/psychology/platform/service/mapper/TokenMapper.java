package com.artur.hasparian.psychology.platform.service.mapper;

import com.artur.hasparian.psychology.platform.repository.dto.TokenDTO;
import com.artur.hasparian.psychology.platform.repository.model.Token;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {UserMapper.class})
public interface TokenMapper {
    TokenDTO toDTO(Token token);

    Token toDAO(TokenDTO token);
}
