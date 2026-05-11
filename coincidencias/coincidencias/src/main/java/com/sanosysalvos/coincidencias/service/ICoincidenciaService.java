package com.sanosysalvos.coincidencias.service;


import com.sanosysalvos.coincidencias.model.Coincidencia;
import java.util.List;
import java.util.Optional;

public interface ICoincidenciaService {
    Coincidencia procesarNuevaCoincidencia(Long petId, Long orgId);

    List<Coincidencia> listarTodas();
    Optional<Coincidencia> buscarPorId(Long id);
    Coincidencia actualizarEstado(Long id, String nuevoEstado);
    void eliminar(Long id);
}