package co.pragma.api.handler;

import co.pragma.api.adapters.ResponseService;
import co.pragma.model.reporteprestamos.ReportePrestamos;
import co.pragma.usecase.reportarprestamo.ConsultarReporteUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportePrestamosHandlerTest {

    @Mock
    private ConsultarReporteUseCase consultarReporteUseCase;

    @Mock
    private ResponseService responseService;

    @Mock
    private ServerRequest serverRequest;

    @InjectMocks
    private ReportePrestamosHandler handler;

    @Test
    void shouldReturnOkResponseWhenReporteIsObtained() {
        ReportePrestamos reporte = ReportePrestamos.builder().build();
        when(consultarReporteUseCase.execute()).thenReturn(Mono.just(reporte));
        when(responseService.okJson(any(ReportePrestamos.class)))
                .thenReturn(ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(reporte));

        Mono<ServerResponse> responseMono = handler.listenConsultarReporteGeneral(serverRequest);

        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertThat(response.statusCode()).isEqualTo(HttpStatus.OK);
                })
                .verifyComplete();

        verify(consultarReporteUseCase).execute();
        verify(responseService).okJson(reporte);
    }
}
