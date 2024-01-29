package com.iche.xpresspayapi.configuration;import io.swagger.v3.oas.annotations.ExternalDocumentation;import io.swagger.v3.oas.annotations.OpenAPIDefinition;import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;import io.swagger.v3.oas.annotations.info.Contact;import io.swagger.v3.oas.annotations.info.Info;import io.swagger.v3.oas.annotations.info.License;import io.swagger.v3.oas.annotations.security.SecurityRequirement;import io.swagger.v3.oas.annotations.security.SecurityScheme;import io.swagger.v3.oas.annotations.servers.Server;import org.springframework.context.annotation.Configuration;@Configuration@OpenAPIDefinition(        info = @Info(                title = "spring boot App REST APIs",                description = "spring boot Xpress-pay REST APIs Documentation",                version = "v.10",                contact = @Contact(                        name = "ichebadu",                        email = "chukwu.iche@gmail.com",                        url = "https://localHost/xpresspay.com"                ),                license =  @License(                        name = "Apache 2.1.0",                        url = "https://test.xpresspayments.com:8099/home"                )        ),        externalDocs =  @ExternalDocumentation(                description = "Spring Boot XPRESS PAY API Documentation",                url = "https://test.xpresspayments.com:8099/home"        ),        servers = {                @Server(                        description = "STAGE ENV",                        url = "http://localhost:8011"                )        },        security = {                @SecurityRequirement(name = "Bearer Authentication")        })@SecurityScheme(        name = "Bearer Authentication",        description = "JWT Authentication",        scheme = "bearer",        type = SecuritySchemeType.HTTP,        bearerFormat = "JWT",        in = SecuritySchemeIn.HEADER)public class SwaggerConfig {}