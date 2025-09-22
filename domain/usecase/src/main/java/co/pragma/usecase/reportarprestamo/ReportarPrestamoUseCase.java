package co.pragma.usecase.reportarprestamo;

import co.pragma.model.prestamo.Prestamo;
import co.pragma.model.reporteprestamos.gateways.ReportePrestamosRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReportarPrestamoUseCase {

    private final ReportePrestamosRepository repository;

    public Mono<Void> execute(Prestamo prestamo) {
        Long monto = prestamo.getMonto().longValue();
        return repository.incrementarAprobados(monto);
    }
}
