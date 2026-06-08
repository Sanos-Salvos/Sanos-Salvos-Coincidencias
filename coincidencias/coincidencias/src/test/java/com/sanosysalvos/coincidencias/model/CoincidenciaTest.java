package com.sanosysalvos.coincidencias.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CoincidenciaTest {

    @Test
    void testNoArgsConstructor() {
        Coincidencia c = new Coincidencia();
        assertNull(c.getId());
        assertNull(c.getPetId());
        assertNull(c.getOrganizacionId());
        assertNull(c.getEstado());
        assertNull(c.getFechaCreacion());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Coincidencia c = new Coincidencia(1L, 10L, 20L, "PENDIENTE", now);

        assertEquals(1L, c.getId());
        assertEquals(10L, c.getPetId());
        assertEquals(20L, c.getOrganizacionId());
        assertEquals("PENDIENTE", c.getEstado());
        assertEquals(now, c.getFechaCreacion());
    }

    @Test
    void testSettersAndGetters() {
        Coincidencia c = new Coincidencia();
        c.setId(5L);
        c.setPetId(100L);
        c.setOrganizacionId(200L);
        c.setEstado("ENCONTRADO");
        LocalDateTime fecha = LocalDateTime.of(2026, 6, 5, 10, 30);
        c.setFechaCreacion(fecha);

        assertEquals(5L, c.getId());
        assertEquals(100L, c.getPetId());
        assertEquals(200L, c.getOrganizacionId());
        assertEquals("ENCONTRADO", c.getEstado());
        assertEquals(fecha, c.getFechaCreacion());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        Coincidencia c1 = new Coincidencia(1L, 10L, 20L, "PENDIENTE", now);
        Coincidencia c2 = new Coincidencia(1L, 10L, 20L, "PENDIENTE", now);

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testNotEquals() {
        Coincidencia c1 = new Coincidencia(1L, 10L, 20L, "PENDIENTE", LocalDateTime.now());
        Coincidencia c2 = new Coincidencia(2L, 10L, 20L, "PENDIENTE", LocalDateTime.now());

        assertNotEquals(c1, c2);
    }

    @Test
    void testToString() {
        Coincidencia c = new Coincidencia(1L, 10L, 20L, "PENDIENTE", LocalDateTime.now());
        String str = c.toString();

        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("petId=10"));
        assertTrue(str.contains("organizacionId=20"));
        assertTrue(str.contains("estado=PENDIENTE"));
    }
}
