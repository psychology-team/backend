package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toDAO(UserDTO user);
}
