package co.pragma.usecase.reportarprestamo;

import co.pragma.model.prestamo.Prestamo;
import co.pragma.model.reporteprestamos.gateways.ReportePrestamosRepository;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReportarPrestamoUseCaseTest {

    @Test
    void shouldIncrementAprobadosWhenPrestamoIsValid() {
        ReportePrestamosRepository repository = mock(ReportePrestamosRepository.class);
        Prestamo prestamo = Prestamo.builder().monto(BigDecimal.valueOf(2850000)).build();
        when(repository.incrementarAprobados(2850000L)).thenReturn(Mono.empty());

        ReportarPrestamoUseCase useCase = new ReportarPrestamoUseCase(repository);

        StepVerifier.create(useCase.execute(prestamo))
                .verifyComplete();
    }

    @Test
    void shouldPropagateErrorWhenRepositoryFails() {
        ReportePrestamosRepository repository = mock(ReportePrestamosRepository.class);
        Prestamo prestamo = Prestamo.builder().monto(BigDecimal.valueOf(3250000)).build();
        when(repository.incrementarAprobados(3250000L)).thenReturn(Mono.error(new RuntimeException("DB_ERROR")));

        ReportarPrestamoUseCase useCase = new ReportarPrestamoUseCase(repository);

        StepVerifier.create(useCase.execute(prestamo))
                .expectErrorMessage("DB_ERROR")
                .verify();
    }
}
