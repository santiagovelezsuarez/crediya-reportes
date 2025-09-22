package co.pragma.dynamodb.helper;

import co.pragma.dynamodb.entity.ReportePrestamosEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient enhancedClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<ReportePrestamosEntity> customerTable;

    private ReportePrestamosEntity modelEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(enhancedClient.table("table_name", TableSchema.fromBean(ReportePrestamosEntity.class)))
                .thenReturn(customerTable);

        modelEntity = new ReportePrestamosEntity();
        modelEntity.setId("reportesGlobales");
        modelEntity.setMontoTotalPrestamosAprobados(10L);
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        ReportePrestamosEntity modelEntityUnderTest = new ReportePrestamosEntity("id", 10L, 125000L);

        assertNotNull(modelEntityUnderTest.getId());
        assertNotNull(modelEntityUnderTest.getMontoTotalPrestamosAprobados());
    }

}