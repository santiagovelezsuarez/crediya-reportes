package co.pragma.model.reporteprestamos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ReportePrestamos {
    private String id;
    private Long cantidadPrestamosAprobados;
    private Long montoTotalPrestamosAprobados;
}
