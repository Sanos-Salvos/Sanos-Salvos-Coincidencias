package com.sanosysalvos.coincidencias.service;

import com.sanosysalvos.coincidencias.factory.ICoincidenciaFactory;
import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.repository.CoincidenciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoincidenciaServiceImplTest {

    @Mock
    private CoincidenciaRepository repository;

    @Mock
    private ICoincidenciaFactory factory;

    @InjectMocks
    private CoincidenciaServiceImpl service;

    private Coincidencia coincidenciaEjemplo;

    @BeforeEach
    void setUp() {
        coincidenciaEjemplo = new Coincidencia(1L, 10L, 20L, "PENDIENTE", LocalDateTime.now());
    }

    // ========== procesarNuevaCoincidencia ==========

    @Test
    void procesarNuevaCoincidencia_deberiaCrearYGuardar() {
        Coincidencia nueva = new Coincidencia(null, 10L, 20L, "PENDIENTE", LocalDateTime.now());
        when(factory.crear(10L, 20L)).thenReturn(nueva);
        when(repository.save(any(Coincidencia.class))).thenReturn(coincidenciaEjemplo);

        Coincidencia result = service.procesarNuevaCoincidencia(10L, 20L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(factory).crear(10L, 20L);
        verify(repository).save(nueva);
    }

    @Test
    void procesarNuevaCoincidencia_deberiaLlamarFactoryYRepository() {
        Coincidencia nueva = new Coincidencia();
        when(factory.crear(anyLong(), anyLong())).thenReturn(nueva);
        when(repository.save(any())).thenReturn(nueva);

        service.procesarNuevaCoincidencia(5L, 15L);

        verify(factory, times(1)).crear(5L, 15L);
        verify(repository, times(1)).save(nueva);
    }

    // ========== listarTodas ==========

    @Test
    void listarTodas_deberiaRetornarLista() {
        Coincidencia c2 = new Coincidencia(2L, 11L, 21L, "ENCONTRADO", LocalDateTime.now());
        when(repository.findAll()).thenReturn(Arrays.asList(coincidenciaEjemplo, c2));

        List<Coincidencia> result = service.listarTodas();

        assertEquals(2, result.size());
        verify(repository).findAll();
    }

    @Test
    void listarTodas_deberiaRetornarListaVacia() {
        when(repository.findAll()).thenReturn(List.of());

        List<Coincidencia> result = service.listarTodas();

        assertTrue(result.isEmpty());
    }

    // ========== buscarPorId ==========

    @Test
    void buscarPorId_deberiaRetornarOptionalConValor() {
        when(repository.findById(1L)).thenReturn(Optional.of(coincidenciaEjemplo));

        Optional<Coincidencia> result = service.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void buscarPorId_deberiaRetornarOptionalVacio() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Coincidencia> result = service.buscarPorId(99L);

        assertFalse(result.isPresent());
    }

    // ========== actualizarEstado ==========

    @Test
    void actualizarEstado_deberiaActualizarYGuardar() {
        when(repository.findById(1L)).thenReturn(Optional.of(coincidenciaEjemplo));
        when(repository.save(any(Coincidencia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Coincidencia result = service.actualizarEstado(1L, "ENCONTRADO");

        assertEquals("ENCONTRADO", result.getEstado());
        verify(repository).save(coincidenciaEjemplo);
    }

    @Test
    void actualizarEstado_deberiaLanzarExcepcionSiNoExiste() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> service.actualizarEstado(99L, "ENCONTRADO"));

        assertTrue(ex.getMessage().contains("no encontrada"));
    }

    // ========== eliminar ==========

    @Test
    void eliminar_deberiaLlamarDeleteById() {
        doNothing().when(repository).deleteById(1L);

        service.eliminar(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void eliminar_deberiaLlamarDeleteByIdConIdCorrecto() {
        doNothing().when(repository).deleteById(42L);

        service.eliminar(42L);

        verify(repository).deleteById(42L);
    }

    // ========== falloGeneral (fallback) ==========

    @Test
    void falloGeneral_deberiaRetornarCoincidenciaFallback() {
        Coincidencia result = service.falloGeneral(1L, 1L, new RuntimeException("test"));

        assertEquals(-1L, result.getId());
        assertEquals("SERVICIO_TEMPORALMENTE_CAIDO", result.getEstado());
    }

    @Test
    void falloGeneral_deberiaIgnorarParametrosDeEntrada() {
        Coincidencia result = service.falloGeneral(999L, 888L, new Exception("error"));

        assertEquals(-1L, result.getId());
        assertNull(result.getPetId());
        assertNull(result.getOrganizacionId());
    }
}
