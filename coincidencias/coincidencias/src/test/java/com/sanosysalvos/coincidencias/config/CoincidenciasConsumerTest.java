package com.sanosysalvos.coincidencias.config;

import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.service.ICoincidenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoincidenciasConsumerTest {

    @Mock
    private ICoincidenciaService service;

    @InjectMocks
    private CoincidenciasConsumer consumer;

    private Coincidencia coincidenciaEjemplo;

    @BeforeEach
    void setUp() {
        coincidenciaEjemplo = new Coincidencia(1L, 1L, 1L, "PENDIENTE", LocalDateTime.now());
    }

    @Test
    void escucharMascotas_deberiaProcesarNuevaCoincidencia() {
        // Arrange
        String mensajePrueba = "{\"id\": 1, \"nombre\": \"Firulais\"}";
        when(service.procesarNuevaCoincidencia(1L, 1L)).thenReturn(coincidenciaEjemplo);

        // Act
        consumer.escucharMascotas(mensajePrueba);

        // Assert
        verify(service, times(1)).procesarNuevaCoincidencia(1L, 1L);
    }

    @Test
    void escucharOrganizaciones_deberiaImprimirMensaje() {
        // Arrange
        String mensajePrueba = "{\"id\": 1, \"nombre\": \"Refugio Sanos y Salvos\"}";

        // Act
        // Este método solo ejecuta System.out.println internamente, por lo que llamarlo asegura
        // la ejecución de todas sus líneas (100% de cobertura).
        consumer.escucharOrganizaciones(mensajePrueba);

        // Assert
        // Verificamos que no interactúe con el servicio en este flujo
        verifyNoInteractions(service);
    }
}