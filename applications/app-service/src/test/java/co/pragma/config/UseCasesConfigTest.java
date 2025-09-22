package co.pragma.config;

import co.pragma.model.reporteprestamos.gateways.ReportePrestamosRepository;
import co.pragma.usecase.reportarprestamo.ConsultarReporteUseCase;
import co.pragma.usecase.reportarprestamo.ReportarPrestamoUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UseCasesConfigTest {

    @Test
    void testUseCaseBeansExist() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class)) {
            String[] beanNames = context.getBeanDefinitionNames();

            boolean useCaseBeanFound = false;
            for (String beanName : beanNames) {
                if (beanName.endsWith("UseCase")) {
                    useCaseBeanFound = true;
                    break;
                }
            }

            assertTrue(useCaseBeanFound, "No beans ending with 'Use Case' were found");
        }
    }

    @Configuration
    @Import(UseCasesConfig.class)
    static class TestConfig {

        @Bean
        public ReportePrestamosRepository reportePrestamosRepository() {
            return Mockito.mock(ReportePrestamosRepository.class);
        }

        @Bean
        public ConsultarReporteUseCase consultarReporteUseCase() {
            return Mockito.mock(ConsultarReporteUseCase.class);
        }

        @Bean
        public ReportarPrestamoUseCase reportarPrestamoUseCase() {
            return Mockito.mock(ReportarPrestamoUseCase.class);
        }
    }
}
