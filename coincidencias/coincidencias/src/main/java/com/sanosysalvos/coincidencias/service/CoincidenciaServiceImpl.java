package com.sanosysalvos.coincidencias.service;

import com.sanosysalvos.coincidencias.factory.ICoincidenciaFactory;
import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.repository.CoincidenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoincidenciaServiceImpl implements ICoincidenciaService {
    private final CoincidenciaRepository repository;
    private final ICoincidenciaFactory factory;

    @Override
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
}