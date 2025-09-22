package co.pragma.api;

import co.pragma.api.handler.ReportePrestamosHandler;
import co.pragma.api.security.SecurityHandlerFilter;
import co.pragma.model.reporteprestamos.ReportePrestamos;
import co.pragma.security.PermissionEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class RouterRest {

    private final SecurityHandlerFilter securityFilter;
    private static final String ROUTE = "/api/v1/reportes";

    @Bean
    @RouterOperations(
            @RouterOperation(
                    path = "/api/reportes",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.GET,
                    beanClass = ReportePrestamosHandler.class,
                    beanMethod = "listenConsultarReporteGeneral",
                    operation = @Operation(
                            operationId = "consultarReporteGeneral",
                            summary = "Consulta el reporte global de préstamos",
                            description = "Obtiene métricas globales de los préstamos aprobados",
                            tags = {"Reportes"},
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Reporte global obtenido correctamente",
                                            content = @Content(schema = @Schema(implementation = ReportePrestamos.class))
                                    ),
                                    @ApiResponse(responseCode = "403", description = "No autorizado"),
                                    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
                            }
                    )
            )
    )
    public RouterFunction<ServerResponse> reporteRoutes(ReportePrestamosHandler reportePrestamosHandler) {
        return route()
                .GET(ROUTE, reportePrestamosHandler::listenConsultarReporteGeneral)
                .filter(securityFilter.requirePermission(PermissionEnum.LEER_REPORTES))
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
