package co.pragma.api.exception;

import co.pragma.api.adapter.ErrorCodeHttpMapper;
import co.pragma.exception.ErrorCode;
import co.pragma.exception.ForbiddenException;
import co.pragma.exception.InfrastructureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class GlobalExceptionHandlerTest {

    @Mock
    private ErrorAttributes errorAttributes;

    @Mock
    private ErrorCodeHttpMapper errorCodeHttpMapper;

    @Mock
    private ServerCodecConfigurer codecConfigurer;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ServerRequest request;

    @Mock
    private ServerResponse response;

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setup() {
        when(codecConfigurer.getReaders()).thenReturn(List.of());
        when(codecConfigurer.getWriters()).thenReturn(List.of());
        when(applicationContext.getClassLoader()).thenReturn(getClass().getClassLoader());
        handler = new GlobalExceptionHandler(errorAttributes, applicationContext, codecConfigurer, errorCodeHttpMapper);
    }

    @Test
    void shouldHandleInfrastructureException() {
        var ex = new InfrastructureException("DB_ERROR", new RuntimeException("Connection failed"));

        when(errorAttributes.getError(request)).thenReturn(ex);

        Mono<ServerResponse> responseMono = Objects.requireNonNull(handler.getRoutingFunction(errorAttributes)
                        .route(request)
                        .block())
                .handle(request);

        StepVerifier.create(responseMono)
                .assertNext(serverResponse -> assertThat(serverResponse.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR))
                .verifyComplete();
    }

    @Test
    void shouldLogBusinessException(CapturedOutput output) {
        var ex = new ForbiddenException();

        when(request.path()).thenReturn("/error");
        when(request.method()).thenReturn(HttpMethod.GET);

        handler.logError(request, response, ex);

        assertTrue(output.getOut().contains("BusinessException at GET /error"));
    }

    @Test
    void shouldLogInfrastructureException(CapturedOutput output) {
        var ex = new InfrastructureException("DB_ERROR", new RuntimeException("Connection failed"));

        when(request.path()).thenReturn("/error");
        when(request.method()).thenReturn(HttpMethod.GET);

        handler.logError(request, response, ex);

        assertTrue(output.getOut().contains("InfrastructureException at GET /error:"));
    }

    @Test
    void shouldLogServerWebInputException(CapturedOutput output) {
        var ex = new ServerWebInputException(ErrorCode.INVALID_REQUEST.name());

        when(request.path()).thenReturn("/error");
        when(request.method()).thenReturn(HttpMethod.POST);

        handler.logError(request, response, ex);

        assertTrue(output.getOut().contains("Web input error at POST /error:"));
    }
}
