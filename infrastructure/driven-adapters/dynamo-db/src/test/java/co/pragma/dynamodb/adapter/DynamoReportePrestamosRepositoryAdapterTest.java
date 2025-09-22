package co.pragma.dynamodb.adapter;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

class DynamoReportePrestamosRepositoryAdapterTest {

    @Test
    void shouldIncrementAprobadosSuccessfully() {
        DynamoDbAsyncClient dynamo = mock(DynamoDbAsyncClient.class);
        when(dynamo.updateItem(any(UpdateItemRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(UpdateItemResponse.builder().build()));

        DynamoReportePrestamosRepositoryAdapter repository = new DynamoReportePrestamosRepositoryAdapter(dynamo);

        StepVerifier.create(repository.incrementarAprobados(2750000L))
                .verifyComplete();
    }

    @Test
    void shouldLogErrorWhenIncrementAprobadosFails() {
        DynamoDbAsyncClient dynamo = mock(DynamoDbAsyncClient.class);
        when(dynamo.updateItem(any(UpdateItemRequest.class)))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("DB_ERROR")));

        DynamoReportePrestamosRepositoryAdapter repository = new DynamoReportePrestamosRepositoryAdapter(dynamo);

        StepVerifier.create(repository.incrementarAprobados(3200000L))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void shouldReturnReportePrestamosWhenDataExists() {
        DynamoDbAsyncClient dynamo = mock(DynamoDbAsyncClient.class);
        Map<String, AttributeValue> item = Map.of(
                "id", AttributeValue.fromS("reportesGlobales"),
                "cantidadPrestamosAprobados", AttributeValue.fromN("5"),
                "montoTotalPrestamosAprobados", AttributeValue.fromN("8250000")
        );
        when(dynamo.getItem(any(GetItemRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(GetItemResponse.builder().item(item).build()));

        DynamoReportePrestamosRepositoryAdapter repository = new DynamoReportePrestamosRepositoryAdapter(dynamo);

        StepVerifier.create(repository.obtenerReporte())
                .expectNextMatches(reporte ->
                        reporte.getId().equals("reportesGlobales") &&
                                reporte.getCantidadPrestamosAprobados() == 5L &&
                                reporte.getMontoTotalPrestamosAprobados() == 8250000L)
                .verifyComplete();
    }

    @Test
    void shouldReturnDefaultReportePrestamosWhenDataDoesNotExist() {
        DynamoDbAsyncClient dynamo = mock(DynamoDbAsyncClient.class);
        when(dynamo.getItem(any(GetItemRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(GetItemResponse.builder().item(Map.of()).build()));

        DynamoReportePrestamosRepositoryAdapter repository = new DynamoReportePrestamosRepositoryAdapter(dynamo);

        StepVerifier.create(repository.obtenerReporte())
                .expectNextMatches(reporte ->
                        reporte.getId().equals("reportesGlobales") &&
                                reporte.getCantidadPrestamosAprobados() == 0L &&
                                reporte.getMontoTotalPrestamosAprobados() == 0L)
                .verifyComplete();
    }

    @Test
    void shouldLogErrorWhenObtenerReporteFails() {
        DynamoDbAsyncClient dynamo = mock(DynamoDbAsyncClient.class);
        when(dynamo.getItem(any(GetItemRequest.class)))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("DB_ERROR")));

        DynamoReportePrestamosRepositoryAdapter repository = new DynamoReportePrestamosRepositoryAdapter(dynamo);

        StepVerifier.create(repository.obtenerReporte())
                .expectError(RuntimeException.class)
                .verify();
    }
}
