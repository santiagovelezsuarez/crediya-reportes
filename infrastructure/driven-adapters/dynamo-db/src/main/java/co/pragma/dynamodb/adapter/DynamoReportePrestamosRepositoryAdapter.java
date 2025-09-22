package co.pragma.dynamodb.adapter;

import co.pragma.model.reporteprestamos.ReportePrestamos;
import co.pragma.model.reporteprestamos.gateways.ReportePrestamosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;

import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DynamoReportePrestamosRepositoryAdapter implements ReportePrestamosRepository {

    private final DynamoDbAsyncClient dynamo;
    private static final String GLOBAL_ID = "reportesGlobales";

    @Override
    public Mono<Void> incrementarAprobados(Long monto) {
        return Mono.fromFuture(() -> dynamo.updateItem(updateRequest(monto)))
                .doOnError(e -> log.error("Error incrementando reporte", e))
                .then();
    }

    @Override
    public Mono<ReportePrestamos> obtenerReporte() {
        return Mono.fromFuture(() -> dynamo.getItem(getRequest()))
                .map(this::toModel)
                .doOnError(e -> log.error("Error obteniendo reporte", e));
    }

    private UpdateItemRequest updateRequest(Long monto) {
        return UpdateItemRequest.builder()
                .tableName("reportes")
                .key(Map.of("id", AttributeValue.fromS(GLOBAL_ID)))
                .updateExpression("ADD cantidadPrestamosAprobados :inc, montoTotalPrestamosAprobados :monto")
                .expressionAttributeValues(Map.of(
                        ":inc", AttributeValue.fromN("1"),
                        ":monto", AttributeValue.fromN(monto.toString())
                ))
                .build();
    }

    private GetItemRequest getRequest() {
        return GetItemRequest.builder()
                .tableName("reportes")
                .key(Map.of("id", AttributeValue.fromS(GLOBAL_ID)))
                .build();
    }

    private ReportePrestamos toModel(GetItemResponse response) {
        var item = response.item();
        if (item.isEmpty()) {
            return ReportePrestamos.builder()
                    .id(GLOBAL_ID)
                    .cantidadPrestamosAprobados(0L)
                    .montoTotalPrestamosAprobados(0L)
                    .build();
        }

        return ReportePrestamos.builder()
                .id(item.get("id").s())
                .cantidadPrestamosAprobados(Long.valueOf(item.getOrDefault("cantidadPrestamosAprobados", AttributeValue.fromN("0")).n()))
                .montoTotalPrestamosAprobados(Long.valueOf(item.getOrDefault("montoTotalPrestamosAprobados", AttributeValue.fromN("0")).n()))
                .build();
    }
}
