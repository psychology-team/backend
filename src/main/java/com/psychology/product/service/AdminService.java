package com.psychology.product.service;

import com.psychology.product.repository.dto.UserDTO;

import java.util.UUID;

public interface AdminService {

    /**
     * Retrieves the current client information based on the given client ID.
     *
     * @param clientId the unique identifier of the client
     * @return the UserDTO containing the client's information
     */
    UserDTO getCurrentClient(UUID clientId);

    /**
     * Disables the client with the given ID.
     *
     * @param id the unique identifier of the client to be disabled
     */
    void disableClient(UUID id);

    /**
     * Deletes the client with the given UUID.
     *
     * @param uuid the unique identifier of the client to be deleted
     */
    void deleteClient(UUID uuid);
}
