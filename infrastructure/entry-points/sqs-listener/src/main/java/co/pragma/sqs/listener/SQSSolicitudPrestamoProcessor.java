package co.pragma.sqs.listener;

import co.pragma.model.prestamo.Prestamo;
import co.pragma.usecase.reportarprestamo.ReportarPrestamoUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class SQSSolicitudPrestamoProcessor implements Function<Message, Mono<Void>> {

    private final ObjectMapper objectMapper;
    private final ReportarPrestamoUseCase useCase;

    @Override
    public Mono<Void> apply(Message message) {
        log.info("Mensaje recibido desde SQSEstadoSolicitudes: {}", message.body());
        try {
            Prestamo prestamo = objectMapper.readValue(message.body(), Prestamo.class);
            log.info("prestamo parseado: {}", prestamo);

            if ("APROBADA".equalsIgnoreCase(prestamo.getEstado())) {
                log.info("Procesando solicitud APROBADA");
                return useCase.execute(prestamo);
            } else {
                log.info("Solicitud {} ignorada, estado={}", prestamo.getCodigoSolicitud(), prestamo.getEstado());
                return Mono.empty();
            }

        } catch (Exception e) {
            log.error("Error procesando mensaje: {}", message.body(), e);
            return Mono.error(e);
        }
    }
}
