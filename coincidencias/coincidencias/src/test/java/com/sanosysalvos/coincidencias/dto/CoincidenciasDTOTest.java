package com.sanosysalvos.coincidencias.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoincidenciasDTOTest {

    @Test
    void testNoArgsConstructor() {
        CoincidenciasDTO dto = new CoincidenciasDTO();
        assertNull(dto.getId());
        assertNull(dto.getPetId());
        assertNull(dto.getMensaje());
    }

    @Test
    void testAllArgsConstructor() {
        CoincidenciasDTO dto = new CoincidenciasDTO(1L, 10L, "Registrada con éxito");

        assertEquals(1L, dto.getId());
        assertEquals(10L, dto.getPetId());
        assertEquals("Registrada con éxito", dto.getMensaje());
    }

    @Test
    void testSettersAndGetters() {
        CoincidenciasDTO dto = new CoincidenciasDTO();
        dto.setId(5L);
        dto.setPetId(100L);
        dto.setMensaje("Test mensaje");

        assertEquals(5L, dto.getId());
        assertEquals(100L, dto.getPetId());
        assertEquals("Test mensaje", dto.getMensaje());
    }

    @Test
    void testEqualsAndHashCode() {
        CoincidenciasDTO dto1 = new CoincidenciasDTO(1L, 10L, "msg");
        CoincidenciasDTO dto2 = new CoincidenciasDTO(1L, 10L, "msg");

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testNotEquals() {
        CoincidenciasDTO dto1 = new CoincidenciasDTO(1L, 10L, "msg1");
        CoincidenciasDTO dto2 = new CoincidenciasDTO(2L, 10L, "msg2");

        assertNotEquals(dto1, dto2);
    }
}
