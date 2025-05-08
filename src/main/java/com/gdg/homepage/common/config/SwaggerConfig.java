package com.gdg.homepage.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("GDG-homepage")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(openApi -> {
                    openApi.info(new Info()
                            .title("GDG API")
                            .description("GDG homepage를 위한 API")
                            .version("1.0.0"));

                    // JWT Security 추가
                    openApi.addSecurityItem(new SecurityRequirement().addList("JWT Authorization"))
                            .schemaRequirement("JWT Authorization",
                                    new SecurityScheme()
                                            .name("Authorization")
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")
                                            .in(SecurityScheme.In.HEADER));
                })
                .build();
    }
}