package com.psychology.product.service;

import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.UserDAO;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(UserDAO user);
}
