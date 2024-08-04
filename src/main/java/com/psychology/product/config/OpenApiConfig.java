package com.psychology.product.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Value("${developer.contact.names}")
    private String contactName;

    @Value("${developer.contact.email}")
    private String contactEmail;

    @Value("${application.server.host}")
    private String serverHost;

    @Value("${application.server.port}")
    private String serverPort;

    @Value("${application.name}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("OpenApi specification - " + applicationName)
                        .version("1.0")
                        .description("OpenApi documentation for Rest API")
                        .contact(new Contact()
                                .name(contactName)
                                .email(contactEmail)
                                .url(serverHost + ":" + serverPort)
                        )
                )
                .addServersItem(new Server()
                        .url(serverHost + ":" + serverPort)
                        .description(applicationName)
                )
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .name("Bearer Authentication")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                        )
                );
    }
}


