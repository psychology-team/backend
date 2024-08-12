package com.psychology.product.service.impl;

import com.psychology.product.repository.AdminRepository;
import com.psychology.product.repository.dto.UserDTO;
import com.psychology.product.repository.model.User;
import com.psychology.product.service.AdminService;
import com.psychology.product.service.mapper.UserMapper;
import com.psychology.product.service.UserService;
import com.psychology.product.util.exception.ConflictException;
import com.psychology.product.util.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserDTO getCurrentClient(UUID clientId) {
        Optional<User> userOptional = adminRepository.findById(clientId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return userMapper.toDTO(user);
        }
        throw new NotFoundException("User not found");
    }

    @Override
    public void disableClient(UUID clientId) {
        Optional<User> userOptional = adminRepository.findById(clientId);
        if (userOptional.isPresent()) {
            User user = adminRepository.getReferenceById(clientId);
            if (user.getRevoked() && !user.getEnabled()) {
                throw new ConflictException("Client was disabled");
            }
            user.setRevoked(true);
            user.setEnabled(false);
            adminRepository.save(user);
        }
    }

    @Override
    public void deleteClient(UUID clientId) {
        Optional<User> userOptional = adminRepository.findById(clientId);
        if (userOptional.isPresent()) {
            adminRepository.deleteById(clientId);
        } else {
            throw new ConflictException("Client not found");
        }
    }
}
