package co.pragma.model.reporteprestamos.gateways;

import co.pragma.model.reporteprestamos.ReportePrestamos;
import reactor.core.publisher.Mono;

public interface ReportePrestamosRepository {

    Mono<Void> incrementarAprobados(Long monto);

    Mono<ReportePrestamos> obtenerReporte();

}
