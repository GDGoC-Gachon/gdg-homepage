package com.gdg.homepage.common.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi boardGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("GDG-homepage")
                .pathsToMatch("/**") // 모든 API 경로 포함
                .addOpenApiCustomizer(
                        openApi ->
                                openApi.setInfo(
                                        new Info()
                                                .title("GDG api")
                                                .description("GDG homepage를 위한 API")
                                                .version("1.0.0")
                                )
                )
                .build();
    }
}
