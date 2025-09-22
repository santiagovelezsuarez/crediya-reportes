package co.pragma.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import security.PermissionValidator;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        var config = new org.springframework.web.cors.CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8082/swagger-ui");
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        var source = new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public PermissionValidator permissionValidator() {
        return new PermissionValidator();
    }
}
