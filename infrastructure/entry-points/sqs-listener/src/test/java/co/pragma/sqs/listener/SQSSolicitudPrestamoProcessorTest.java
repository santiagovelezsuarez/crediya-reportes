package co.pragma.sqs.listener;

import co.pragma.model.prestamo.Prestamo;
import co.pragma.usecase.reportarprestamo.ReportarPrestamoUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.amazon.awssdk.services.sqs.model.Message;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SQSSolicitudPrestamoProcessorTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ReportarPrestamoUseCase useCase;

    @InjectMocks
    private SQSSolicitudPrestamoProcessor processor;

    private static final String MENSAJE_APROBADO = "{\"codigoSolicitud\": \"123\", \"estado\": \"APROBADA\"}";
    private static final String MENSAJE_RECHAZADO = "{\"codigoSolicitud\": \"456\", \"estado\": \"RECHAZADA\"}";
    private static final String MENSAJE_INVALIDO = "mensaje invalido";


    @Test
    void shouldProcessMessageWhenStateIsAprobada() throws JsonProcessingException {
        var message = Message.builder().body(MENSAJE_APROBADO).build();
        var prestamo = Prestamo.builder().codigoSolicitud("123").estado("APROBADA").build();

        when(objectMapper.readValue(MENSAJE_APROBADO, Prestamo.class)).thenReturn(prestamo);
        when(useCase.execute(any(Prestamo.class))).thenReturn(Mono.empty());

        StepVerifier.create(processor.apply(message)).verifyComplete();
        verify(useCase).execute(prestamo);
    }

    @Test
    void shouldIgnoreMessageWhenStateIsNotAprobada() throws JsonProcessingException {
        var message = Message.builder().body(MENSAJE_RECHAZADO).build();
        var prestamo = Prestamo.builder().codigoSolicitud("456").estado("RECHAZADA").build();

        when(objectMapper.readValue(MENSAJE_RECHAZADO, Prestamo.class)).thenReturn(prestamo);

        StepVerifier.create(processor.apply(message)).verifyComplete();
        verify(useCase, never()).execute(any());
    }

    @Test
    void shouldReturnErrorWhenMessageIsInvalidJson() throws JsonProcessingException {
        var message = Message.builder().body(MENSAJE_INVALIDO).build();
        when(objectMapper.readValue(MENSAJE_INVALIDO, Prestamo.class)).thenThrow(JsonProcessingException.class);

        StepVerifier.create(processor.apply(message)).verifyError(JsonProcessingException.class);
        verify(useCase, never()).execute(any());
    }
}