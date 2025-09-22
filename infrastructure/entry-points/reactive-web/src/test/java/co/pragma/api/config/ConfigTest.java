package co.pragma.api.config;

import co.pragma.api.adapters.ResponseService;
import co.pragma.api.handler.ReportePrestamosHandler;
import co.pragma.api.RouterRest;
import co.pragma.model.reporteprestamos.gateways.ReportePrestamosRepository;
import co.pragma.usecase.reportarprestamo.ConsultarReporteUseCase;
import co.pragma.usecase.reportarprestamo.ReportarPrestamoUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration(classes = {RouterRest.class, ReportePrestamosHandler.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private ConsultarReporteUseCase consultarReporteUseCase;

    @MockitoBean
    private ReportarPrestamoUseCase reportarPrestamoUseCase;

    @MockitoBean
    private ReportePrestamosRepository reportePrestamosRepository;

    @MockitoBean
    private ResponseService responseService;

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