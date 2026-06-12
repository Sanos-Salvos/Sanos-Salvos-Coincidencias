package com.sanosysalvos.coincidencias.config;

import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.service.ICoincidenciaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoincidenciasConsumerTest {

    @Mock
    private ICoincidenciaService service;

    @InjectMocks
    private CoincidenciasConsumer consumer;

    @Test
    void escucharMascotas_deberiaLlamarProcesarNuevaCoincidencia() {
        Coincidencia mockResult = new Coincidencia();
        when(service.procesarNuevaCoincidencia(1L, 1L)).thenReturn(mockResult);

        consumer.escucharMascotas("{\"petId\":1}");

        verify(service).procesarNuevaCoincidencia(1L, 1L);
    }

    @Test
    void escucharMascotas_deberiaFuncionarConMensajeVacio() {
        Coincidencia mockResult = new Coincidencia();
        when(service.procesarNuevaCoincidencia(1L, 1L)).thenReturn(mockResult);

        consumer.escucharMascotas("");

        verify(service).procesarNuevaCoincidencia(1L, 1L);
    }

    @Test
    void escucharOrganizaciones_noDeberiaLlamarService() {
        consumer.escucharOrganizaciones("{\"orgId\":1}");

        verifyNoInteractions(service);
    }

    @Test
    void escucharOrganizaciones_deberiaFuncionarConMensajeNull() {
        consumer.escucharOrganizaciones(null);

        verifyNoInteractions(service);
    }
}
