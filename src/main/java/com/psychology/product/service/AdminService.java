package com.psychology.product.service;

import com.psychology.product.repository.dto.UserDTO;

import java.util.UUID;

public interface AdminService {
    UserDTO getCurrentClient(UUID clientId);

    void disableClient(UUID id);

    void deleteClient(UUID uuid);
}
