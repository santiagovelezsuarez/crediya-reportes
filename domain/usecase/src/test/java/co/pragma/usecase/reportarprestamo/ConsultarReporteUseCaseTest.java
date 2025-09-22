package co.pragma.usecase.reportarprestamo;

import co.pragma.model.reporteprestamos.ReportePrestamos;
import co.pragma.model.reporteprestamos.gateways.ReportePrestamosRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsultarReporteUseCaseTest {

    @Test
    void shouldReturnReportePrestamosWhenRepositoryReturnsData() {
        ReportePrestamosRepository repository = mock(ReportePrestamosRepository.class);
        ReportePrestamos expectedReporte = new ReportePrestamos();
        when(repository.obtenerReporte()).thenReturn(Mono.just(expectedReporte));

        ConsultarReporteUseCase useCase = new ConsultarReporteUseCase(repository);

        StepVerifier.create(useCase.execute())
                .expectNext(expectedReporte)
                .verifyComplete();
    }

    @Test
    void shouldReturnEmptyWhenRepositoryReturnsEmpty() {
        ReportePrestamosRepository repository = mock(ReportePrestamosRepository.class);
        when(repository.obtenerReporte()).thenReturn(Mono.empty());

        ConsultarReporteUseCase useCase = new ConsultarReporteUseCase(repository);

        StepVerifier.create(useCase.execute())
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenRepositoryThrowsError() {
        ReportePrestamosRepository repository = mock(ReportePrestamosRepository.class);
        when(repository.obtenerReporte()).thenReturn(Mono.error(new RuntimeException("DB_ERROR")));

        ConsultarReporteUseCase useCase = new ConsultarReporteUseCase(repository);

        StepVerifier.create(useCase.execute())
                .expectErrorMessage("DB_ERROR")
                .verify();
    }
}
