package co.pragma.api;

import co.pragma.api.handler.ReportePrestamosHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

@ContextConfiguration(classes = {RouterRest.class, ReportePrestamosHandler.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

}
