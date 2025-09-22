package co.pragma.model.prestamo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Prestamo {
    private String codigoSolicitud;
    private BigDecimal monto;
    private String estado;
}
