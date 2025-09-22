package co.pragma.model.reporteprestamos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReportePrestamosTest {

    @Test
    void shouldBuildReportePrestamosWithAllFields() {
        ReportePrestamos reporte = ReportePrestamos.builder()
                .id("reportesGlobales")
                .cantidadPrestamosAprobados(5L)
                .montoTotalPrestamosAprobados(15750000L)
                .build();

        assertEquals("reportesGlobales", reporte.getId());
        assertEquals(5L, reporte.getCantidadPrestamosAprobados());
        assertEquals(15750000L, reporte.getMontoTotalPrestamosAprobados());
    }

    @Test
    void shouldUpdateReportePrestamosFieldsUsingToBuilder() {
        ReportePrestamos original = ReportePrestamos.builder()
                .id("reportesGlobales")
                .cantidadPrestamosAprobados(5L)
                .montoTotalPrestamosAprobados(1500000L)
                .build();

        ReportePrestamos updated = original.toBuilder()
                .cantidadPrestamosAprobados(10L)
                .montoTotalPrestamosAprobados(3000000L)
                .build();

        assertEquals("reportesGlobales", updated.getId());
        assertEquals(10L, updated.getCantidadPrestamosAprobados());
        assertEquals(3000000L, updated.getMontoTotalPrestamosAprobados());
    }

    @Test
    void shouldCreateReportePrestamosWithNoArgsConstructor() {
        ReportePrestamos reporte = new ReportePrestamos();

        assertNotNull(reporte);
    }

    @Test
    void shouldSetAndGetFieldsCorrectly() {
        ReportePrestamos reporte = new ReportePrestamos();
        reporte.setId("reportesGlobales");
        reporte.setCantidadPrestamosAprobados(3L);
        reporte.setMontoTotalPrestamosAprobados(5700000L);

        assertEquals("reportesGlobales", reporte.getId());
        assertEquals(3L, reporte.getCantidadPrestamosAprobados());
        assertEquals(5700000L, reporte.getMontoTotalPrestamosAprobados());
    }
}
