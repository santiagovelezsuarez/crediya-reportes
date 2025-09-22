package co.pragma.api;

import co.pragma.api.handler.ReportePrestamosHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {

    //private final SecurityHandlerFilter securityFilter;
    private static final String ROUTE = "/api/v1/reportes";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(ReportePrestamosHandler reportePrestamosHandler) {
        return route()
                .GET(ROUTE, reportePrestamosHandler::listenConsultarReporteGeneral)
                //.filter(securityFilter.requirePermission(PermissionEnum.LISTAR_SOLICITUDES_PENDIENTES))
                .build();
    }

    @Bean
    @RouterOperations(
            @RouterOperation(
                    path = "/api/health",
                    produces = {MediaType.TEXT_PLAIN_VALUE},
                    method = RequestMethod.GET,
                    operation = @Operation(
                            operationId = "healthCheck",
                            summary = "Health check",
                            description = "Verifica que el servicio está disponible",
                            tags = {"Health"},
                            responses = {
                                    @ApiResponse(responseCode = "200", description = "Servicio disponible")
                            }
                    )
            )
    )
    public RouterFunction<ServerResponse> healthRoutes() {
        return route()
                .GET("/api/health", r -> ServerResponse.ok().bodyValue("OK"))
                .build();
    }
}
