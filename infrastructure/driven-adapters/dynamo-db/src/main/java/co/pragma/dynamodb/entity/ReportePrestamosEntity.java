package co.pragma.dynamodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class ReportePrestamosEntity {
    private String id;
    private Long cantidadPrestamosAprobados = 0L;
    private Long montoTotalPrestamosAprobados = 0L;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}
