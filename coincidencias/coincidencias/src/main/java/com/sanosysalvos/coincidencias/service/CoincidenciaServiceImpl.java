package com.sanosysalvos.coincidencias.service;

import com.sanosysalvos.coincidencias.factory.ICoincidenciaFactory;
import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.repository.CoincidenciaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoincidenciaServiceImpl implements ICoincidenciaService {

    @Autowired
    private CoincidenciaRepository repository;

    @Autowired
    private ICoincidenciaFactory factory;
    @Override
    @CircuitBreaker(name = "coincidenciasCB", fallbackMethod = "falloGeneral")
    public Coincidencia procesarNuevaCoincidencia(Long petId, Long orgId) {
        Coincidencia nueva = factory.crear(petId, orgId);
        return repository.save(nueva);
    }

    @Override
    public List<Coincidencia> listarTodas() {
        return repository.findAll();
    }

    @Override
    public Optional<Coincidencia> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Coincidencia actualizarEstado(Long id, String nuevoEstado) {
        return repository.findById(id).map(c -> {
            c.setEstado(nuevoEstado);
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Coincidencia no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    // Método de resguardo (Fallback) para el Circuit Breaker
    public Coincidencia falloGeneral(Long petId, Long orgId, Throwable t) {
        Coincidencia fallback = new Coincidencia();
        fallback.setId(-1L);
        fallback.setEstado("SERVICIO_TEMPORALMENTE_CAIDO");
        return fallback;
    }
}