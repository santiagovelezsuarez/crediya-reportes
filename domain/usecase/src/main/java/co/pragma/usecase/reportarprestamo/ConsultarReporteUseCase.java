package co.pragma.usecase.reportarprestamo;

import co.pragma.model.reporteprestamos.ReportePrestamos;
import co.pragma.model.reporteprestamos.gateways.ReportePrestamosRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ConsultarReporteUseCase {

    private final ReportePrestamosRepository repository;

    public Mono<ReportePrestamos> execute() {
        return repository.obtenerReporte();
    }
}
