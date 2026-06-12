package com.sanosysalvos.coincidencias.factory;

import com.sanosysalvos.coincidencias.model.Coincidencia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoincidenciaFactoryImplTest {

    private CoincidenciaFactoryImpl factory;

    @BeforeEach
    void setUp() {
        factory = new CoincidenciaFactoryImpl();
    }

    @Test
    void crear_deberiaRetornarCoincidenciaConDatosCorrectos() {
        Coincidencia result = factory.crear(10L, 20L);

        assertNotNull(result);
        assertEquals(10L, result.getPetId());
        assertEquals(20L, result.getOrganizacionId());
    }

    @Test
    void crear_deberiaEstablecerEstadoPendiente() {
        Coincidencia result = factory.crear(1L, 1L);

        assertEquals("PENDIENTE", result.getEstado());
    }

    @Test
    void crear_deberiaEstablecerFechaCreacion() {
        Coincidencia result = factory.crear(1L, 1L);

        assertNotNull(result.getFechaCreacion());
    }

    @Test
    void crear_deberiaRetornarIdNull() {
        Coincidencia result = factory.crear(1L, 1L);

        assertNull(result.getId());
    }

    @Test
    void crear_conIdsNulos_deberiaFuncionar() {
        Coincidencia result = factory.crear(null, null);

        assertNotNull(result);
        assertNull(result.getPetId());
        assertNull(result.getOrganizacionId());
        assertEquals("PENDIENTE", result.getEstado());
    }

    @Test
    void crear_deberiaSerInstanciaDeICoincidenciaFactory() {
        assertTrue(factory instanceof ICoincidenciaFactory);
    }
}
