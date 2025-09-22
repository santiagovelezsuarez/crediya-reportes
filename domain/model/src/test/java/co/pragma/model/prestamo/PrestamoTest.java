package co.pragma.model.prestamo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PrestamoTest {

    @Test
    void shouldBuildPrestamoWithAllFields() {
        Prestamo prestamo = Prestamo.builder()
                .codigoSolicitud("SP-12345")
                .monto(BigDecimal.valueOf(1250000))
                .estado("APROBADO")
                .build();

        assertEquals("SP-12345", prestamo.getCodigoSolicitud());
        assertEquals(BigDecimal.valueOf(1250000), prestamo.getMonto());
        assertEquals("APROBADO", prestamo.getEstado());
    }

    @Test
    void shouldUpdatePrestamoFieldsUsingToBuilder() {
        Prestamo original = Prestamo.builder()
                .codigoSolicitud("SP-12345")
                .monto(BigDecimal.valueOf(1250000))
                .estado("PENDIENTE")
                .build();

        Prestamo updated = original.toBuilder()
                .estado("APROBADO")
                .build();

        assertEquals("SP-12345", updated.getCodigoSolicitud());
        assertEquals(BigDecimal.valueOf(1250000), updated.getMonto());
        assertEquals("APROBADO", updated.getEstado());
    }

    @Test
    void shouldCreatePrestamoWithNoArgsConstructor() {
        Prestamo prestamo = new Prestamo();

        assertNotNull(prestamo);
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        Prestamo prestamo = new Prestamo();
        prestamo.setCodigoSolicitud("SP-67890");
        prestamo.setMonto(BigDecimal.valueOf(1375000));
        prestamo.setEstado("RECHAZADO");

        assertEquals("SP-67890", prestamo.getCodigoSolicitud());
        assertEquals(BigDecimal.valueOf(1375000), prestamo.getMonto());
        assertEquals("RECHAZADO", prestamo.getEstado());
    }
}
