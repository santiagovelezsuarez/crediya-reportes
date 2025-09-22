package co.pragma.api.handler;

import co.pragma.api.adapter.ResponseService;
import co.pragma.usecase.reportarprestamo.ConsultarReporteUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportePrestamosHandler {

    private final ConsultarReporteUseCase consultarReporteUseCase;
    private final ResponseService responseService;

    public Mono<ServerResponse> listenConsultarReporteGeneral(ServerRequest serverRequest) {
        log.debug("Petición recibida para consultar reportes de prestamos");
        return consultarReporteUseCase.execute()
                .doOnNext(r -> log.trace("Reporte obtenido {}", r.getId()))
                .flatMap(responseService::okJson);
    }
}
