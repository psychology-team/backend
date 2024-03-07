package com.psychology.product.util;

import org.testcontainers.containers.PostgreSQLContainer;

@SuppressWarnings("resource")
public class ContainerUtil {

    public static PostgreSQLContainer<?> createPostgres() {
        return new PostgreSQLContainer<>("postgres:latest")
                .withUsername("test")
                .withPassword("test")
                .withDatabaseName("test");
    }

}
