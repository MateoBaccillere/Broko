package com.broko.app.wallet_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wallet Service API")
                        .version("1.0.0")
                        .description("Endpoints del servicio de billeteras de Broko")
                        .contact(new Contact()
                                .name("Equipo Broko")
                                .email("soporte@broko.com"))
                );
    }
}
