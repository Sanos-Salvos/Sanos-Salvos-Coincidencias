package com.sanosysalvos.coincidencias.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.service.ICoincidenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoincidenciaController.class)
@AutoConfigureMockMvc(addFilters = false)
class CoincidenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICoincidenciaService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Coincidencia coincidenciaEjemplo;

    @BeforeEach
    void setUp() {
        coincidenciaEjemplo = new Coincidencia(1L, 10L, 20L, "PENDIENTE", LocalDateTime.now());
    }

    // ========== POST /api/coincidencias ==========

    @Test
    @WithMockUser
    void crear_deberiaRetornar200ConDTO() throws Exception {
        when(service.procesarNuevaCoincidencia(10L, 20L)).thenReturn(coincidenciaEjemplo);

        mockMvc.perform(post("/api/coincidencias")
                        .param("petId", "10")
                        .param("orgId", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.petId", is(10)));
    }

    @Test
    void crear_sinAuth_deberiaRetornar400() throws Exception {
        mockMvc.perform(post("/api/coincidencias")
                        .param("petId", "10")
                        .param("orgId", "20"))
                .andExpect(status().isBadRequest());
    }

    // ========== GET /api/coincidencias ==========

    @Test
    @WithMockUser
    void listar_deberiaRetornarLista() throws Exception {
        Coincidencia c2 = new Coincidencia(2L, 11L, 21L, "ENCONTRADO", LocalDateTime.now());
        when(service.listarTodas()).thenReturn(Arrays.asList(coincidenciaEjemplo, c2));

        mockMvc.perform(get("/api/coincidencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @WithMockUser
    void listar_deberiaRetornarListaVacia() throws Exception {
        when(service.listarTodas()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/coincidencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ========== GET /api/coincidencias/{id} ==========

    @Test
    @WithMockUser
    void obtener_deberiaRetornar200SiExiste() throws Exception {
        when(service.buscarPorId(1L)).thenReturn(Optional.of(coincidenciaEjemplo));

        mockMvc.perform(get("/api/coincidencias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.petId", is(10)));
    }

    @Test
    @WithMockUser
    void obtener_deberiaRetornar404SiNoExiste() throws Exception {
        when(service.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/coincidencias/99"))
                .andExpect(status().isNotFound());
    }

    // ========== PUT /api/coincidencias/{id}/estado ==========

    @Test
    @WithMockUser
    void actualizar_deberiaRetornar200() throws Exception {
        Coincidencia actualizada = new Coincidencia(1L, 10L, 20L, "ENCONTRADO", LocalDateTime.now());
        when(service.actualizarEstado(1L, "ENCONTRADO")).thenReturn(actualizada);

        mockMvc.perform(put("/api/coincidencias/1/estado")
                        .param("estado", "ENCONTRADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("ENCONTRADO")));
    }

    @Test
    @WithMockUser
    void actualizar_deberiaRetornar500SiNoExiste() throws Exception {
        when(service.actualizarEstado(anyLong(), anyString()))
                .thenThrow(new RuntimeException("Coincidencia no encontrada"));

        mockMvc.perform(put("/api/coincidencias/99/estado")
                        .param("estado", "ENCONTRADO"))
                .andExpect(status().isBadRequest());
    }

    // ========== DELETE /api/coincidencias/{id} ==========

    @Test
    @WithMockUser
    void eliminar_deberiaRetornar204() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/coincidencias/1"))
                .andExpect(status().isNoContent());

        verify(service).eliminar(1L);
    }
}
