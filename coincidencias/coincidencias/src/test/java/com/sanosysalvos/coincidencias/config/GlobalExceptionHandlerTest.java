package com.sanosysalvos.coincidencias.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Configuramos MockMvc aislando el controlador simulado e inyectando tu ExceptionHandler real
        mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void handleRuntimeException_deberiaRetornar400() throws Exception {
        mockMvc.perform(get("/test/runtime-exception"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Error en el Microservicio de Coincidencias")))
                .andExpect(jsonPath("$.mensaje", is("Error de prueba")))
                .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    void handleRuntimeException_deberiaManejarMensajeNull() throws Exception {
        mockMvc.perform(get("/test/runtime-exception-null"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Error en el Microservicio de Coincidencias")))
                .andExpect(jsonPath("$.mensaje").value(nullValue())) // Valida el comportamiento con mensaje null
                .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    void handleGlobalException_deberiaRetornar500() throws Exception {
        mockMvc.perform(get("/test/exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error", is("Error Interno del Servidor")))
                .andExpect(jsonPath("$.mensaje", is("Ocurrió un error inesperado")))
                .andExpect(jsonPath("$.status", is(500)));
    }

    @Test
    void handleRuntimeException_deberiaTenerBodyNoNull() throws Exception {
        mockMvc.perform(get("/test/runtime-exception"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", not(anEmptyMap())));
    }

    @Test
    void handleGlobalException_deberiaTenerBodyNoNull() throws Exception {
        mockMvc.perform(get("/test/exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", not(anEmptyMap())));
    }

    // ========== Controlador Interno Simulado para Forzar las Excepciones ==========
    @RestController
    static class TestController {

        @GetMapping("/test/runtime-exception")
        public void throwRuntimeException() {
            throw new RuntimeException("Error de prueba");
        }

        @GetMapping("/test/runtime-exception-null")
        public void throwRuntimeExceptionNull() {
            throw new RuntimeException();
        }

        @GetMapping("/test/exception")
        public void throwException() throws Exception {
            throw new Exception("Error inesperado");
        }
    }
}