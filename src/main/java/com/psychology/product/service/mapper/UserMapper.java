package com.psychology.product.service.mapper;

import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.UserDAO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(UserDAO user);
}
