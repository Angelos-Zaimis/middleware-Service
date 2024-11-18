package com.middleware_service.middleware_service.configuration.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "springdoc.swagger-ui.enabled", havingValue = "true")
public class SpringDocConfig {

    @Bean
    @ConditionalOnMissingBean(value = SpringDocConfig.class)
    static OpenAPI openAPI() {
        return new OpenAPI().tags(SpringDocTags.tags());
    }
}
