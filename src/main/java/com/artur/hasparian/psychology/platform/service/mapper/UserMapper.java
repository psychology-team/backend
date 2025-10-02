package com.artur.hasparian.psychology.platform.service.mapper;

import com.artur.hasparian.psychology.platform.repository.dto.UserDTO;
import com.artur.hasparian.psychology.platform.repository.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toDAO(UserDTO user);
}
