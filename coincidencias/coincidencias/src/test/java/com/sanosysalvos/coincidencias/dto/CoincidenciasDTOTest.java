package com.sanosysalvos.coincidencias.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoincidenciasDTOTest {

    @Test
    void testNoArgsConstructor() {
        CoincidenciasDTO dto = new CoincidenciasDTO();
        assertNull(dto.getId());
        assertNull(dto.getPetId());
        assertNull(dto.getOrgId());
        assertNull(dto.getEstado());
        assertNull(dto.getMensaje());
    }

    @Test
    void testAllArgsConstructor() {
        // 💡 Ajustado a los 5 parámetros que tu clase real exige
        CoincidenciasDTO dto = new CoincidenciasDTO(1L, 10L, 20L, "PENDIENTE", "Registrada con éxito");

        assertEquals(1L, dto.getId());
        assertEquals(10L, dto.getPetId());
        assertEquals(20L, dto.getOrgId());
        assertEquals("PENDIENTE", dto.getEstado());
        assertEquals("Registrada con éxito", dto.getMensaje());
    }

    @Test
    void testSettersAndGetters() {
        CoincidenciasDTO dto = new CoincidenciasDTO();
        dto.setId(5L);
        dto.setPetId(100L);
        dto.setOrgId(200L);
        dto.setEstado("ENCONTRADO");
        dto.setMensaje("Test mensaje");

        assertEquals(5L, dto.getId());
        assertEquals(100L, dto.getPetId());
        assertEquals(200L, dto.getOrgId());
        assertEquals("ENCONTRADO", dto.getEstado());
        assertEquals("Test mensaje", dto.getMensaje());
    }

    @Test
    void testEqualsAndHashCode() {
        // 💡 Ajustado a los 5 parámetros requeridos
        CoincidenciasDTO dto1 = new CoincidenciasDTO(1L, 10L, 20L, "PENDIENTE", "msg");
        CoincidenciasDTO dto2 = new CoincidenciasDTO(1L, 10L, 20L, "PENDIENTE", "msg");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testNotEquals() {
        // 💡 Ajustado a los 5 parámetros requeridos
        CoincidenciasDTO dto1 = new CoincidenciasDTO(1L, 10L, 20L, "PENDIENTE", "msg1");
        CoincidenciasDTO dto2 = new CoincidenciasDTO(2L, 10L, 20L, "PENDIENTE", "msg2");

        assertNotEquals(dto1, dto2);
    }
}