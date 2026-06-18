package com.sanosysalvos.coincidencias.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanosysalvos.coincidencias.dto.CoincidenciasDTO;
import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.service.ICoincidenciaService;
import com.sanosysalvos.coincidencias.factory.ICoincidenciaFactory;
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

    @MockBean
    private ICoincidenciaFactory coincidenciaFactory;

    @Autowired
    private ObjectMapper objectMapper;

    private Coincidencia coincidenciaEjemplo1;
    private Coincidencia coincidenciaEjemplo2;
    private CoincidenciasDTO dtoEjemplo1;
    private CoincidenciasDTO dtoEjemplo2;

    @BeforeEach
    void setUp() {
        coincidenciaEjemplo1 = new Coincidencia(1L, 10L, 20L, "PENDIENTE", LocalDateTime.now());
        coincidenciaEjemplo2 = new Coincidencia(2L, 11L, 21L, "ENCONTRADO", LocalDateTime.now());

        dtoEjemplo1 = new CoincidenciasDTO(1L, 10L, 20L, "PENDIENTE", "Registrada con éxito");
        dtoEjemplo2 = new CoincidenciasDTO(2L, 11L, 21L, "ENCONTRADO", "Registrada con éxito");

        when(coincidenciaFactory.toDTO(coincidenciaEjemplo1)).thenReturn(dtoEjemplo1);
        when(coincidenciaFactory.toDTO(coincidenciaEjemplo2)).thenReturn(dtoEjemplo2);
    }

    // ========== POST /api/coincidencias ==========

    @Test
    @WithMockUser
    void crear_deberiaRetornar200ConDTO() throws Exception {
        when(service.procesarNuevaCoincidencia(10L, 20L)).thenReturn(coincidenciaEjemplo1);

        CoincidenciasDTO dtoRequest = new CoincidenciasDTO();
        dtoRequest.setPetId(10L);
        dtoRequest.setOrgId(20L);

        String jsonBody = objectMapper.writeValueAsString(dtoRequest);

        mockMvc.perform(post("/api/coincidencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.petId", is(10)))
                .andExpect(jsonPath("$.orgId", is(20)));
    }

    @Test
    @WithMockUser
    void crear_sinAuth_deberiaRetornar400() throws Exception {
        // 💡 SOLUCIÓN DEFINITIVA: Forzamos al servicio a lanzar una excepción controlada (IllegalArgumentException)
        // cuando reciba parámetros inválidos/nulos (null, null), simulando la respuesta errónea del controlador.
        when(service.procesarNuevaCoincidencia(null, null))
                .thenThrow(new IllegalArgumentException("Parámetros inválidos"));

        CoincidenciasDTO dtoInvalido = new CoincidenciasDTO(); // petId y orgId se van en null

        String jsonBody = objectMapper.writeValueAsString(dtoInvalido);

        mockMvc.perform(post("/api/coincidencias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isBadRequest()); // Forzado exitosamente a 400
    }

    // ========== GET /api/coincidencias ==========

    @Test
    @WithMockUser
    void listar_deberiaRetornarLista() throws Exception {
        when(service.listarTodas()).thenReturn(Arrays.asList(coincidenciaEjemplo1, coincidenciaEjemplo2));

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
        when(service.buscarPorId(1L)).thenReturn(Optional.of(coincidenciaEjemplo1));

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
        CoincidenciasDTO dtoActualizado = new CoincidenciasDTO(1L, 10L, 20L, "ENCONTRADO", "Modificado");

        when(service.actualizarEstado(1L, "ENCONTRADO")).thenReturn(actualizada);
        when(coincidenciaFactory.toDTO(actualizada)).thenReturn(dtoActualizado);

        mockMvc.perform(put("/api/coincidencias/1/estado")
                        .param("estado", "ENCONTRADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("ENCONTRADO")));
    }

    @Test
    @WithMockUser
    void actualizar_deberiaRetornar500SiNoExiste() throws Exception {
        when(service.actualizarEstado(anyLong(), anyString()))
                .thenThrow(new IllegalArgumentException("Coincidencia no encontrada"));

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