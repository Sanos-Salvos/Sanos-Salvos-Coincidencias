package com.sanosysalvos.coincidencias.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler handler;

    @Test
    void handleRuntimeException_deberiaRetornar400() {
        RuntimeException ex = new RuntimeException("Error de prueba");

        ResponseEntity<Map<String, Object>> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("Error en el Microservicio de Coincidencias", body.get("error"));
        assertEquals("Error de prueba", body.get("mensaje"));
        assertEquals(400, body.get("status"));
    }

    @Test
    void handleRuntimeException_deberiaManejarMensajeNull() {
        RuntimeException ex = new RuntimeException();

        ResponseEntity<Map<String, Object>> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertNull(body.get("mensaje"));
    }

    @Test
    void handleGlobalException_deberiaRetornar500() {
        Exception ex = new Exception("Error inesperado");

        ResponseEntity<Map<String, Object>> response = handler.handleGlobalException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals("Error Interno del Servidor", body.get("error"));
        assertEquals("Ocurrió un error inesperado", body.get("mensaje"));
        assertEquals(500, body.get("status"));
    }

    @Test
    void handleRuntimeException_deberiaTenerBodyNoNull() {
        ResponseEntity<Map<String, Object>> response = handler.handleRuntimeException(new RuntimeException("test"));

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void handleGlobalException_deberiaTenerBodyNoNull() {
        ResponseEntity<Map<String, Object>> response = handler.handleGlobalException(new Exception("test"));

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
}
