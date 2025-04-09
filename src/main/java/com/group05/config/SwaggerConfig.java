package com.group05.config;

import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(
        title = "Swagger API Gestión de Pedidos", 
        description = "Documentación de los servicios utilizados para el Sistema de Gestión de Pedidos",
        termsOfService = "localhost:8080/terminos_y_servicios",
        contact = @Contact(
                    name = "support", 
                    email = "support-group05@gmail.com"
        ),
        version = "1.0.0",
        summary = "Documentacion APIs",
        license = @License(
                    name = "GROUP 05", 
                    identifier = "G05",
                    url = "https://github.com/"
        )
    ),
    servers = {
            @Server(
                description = "Servidor Local", url = "http://localhost:8080"
            )
    },
    security = @SecurityRequirement(
        name = "Security Token"
    )
)
@SecurityScheme(
    name = "Security Token",
    description = "Access Token for my API",
    type = SecuritySchemeType.HTTP,
    paramName = HttpHeaders.AUTHORIZATION,
    in = SecuritySchemeIn.HEADER,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

}
