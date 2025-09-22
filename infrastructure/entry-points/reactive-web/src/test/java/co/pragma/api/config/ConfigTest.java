package co.pragma.api.config;

import co.pragma.api.handler.ReportePrestamosHandler;
import co.pragma.api.RouterRest;
import co.pragma.api.security.SecurityHandlerFilter;
import co.pragma.api.security.UserContextExtractor;
import co.pragma.usecase.reportarprestamo.ConsultarReporteUseCase;
import co.pragma.usecase.reportarprestamo.ReportarPrestamoUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import security.PermissionValidator;

@ContextConfiguration(classes = {RouterRest.class, ReportePrestamosHandler.class})
@WebFluxTest
@Import({SecurityConfig.class, CorsConfig.class, SecurityHeadersConfig.class, SecurityHandlerFilter.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ReportePrestamosHandler reportePrestamosHandler;

    @MockitoBean
    private ConsultarReporteUseCase consultarReporteUseCase;

    @MockitoBean
    private ReportarPrestamoUseCase reportarPrestamoUseCase;

    @MockitoBean
    private PermissionValidator permissionValidator;

    @MockitoBean
    private UserContextExtractor userContextExtractor;

    @Test
    void corsConfigurationShouldAllowOrigins() {
        webTestClient.get()
                .uri("/api/health")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Security-Policy", "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }
}