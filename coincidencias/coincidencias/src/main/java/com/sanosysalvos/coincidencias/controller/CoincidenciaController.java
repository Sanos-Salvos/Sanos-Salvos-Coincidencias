package com.sanosysalvos.coincidencias.controller;

import com.sanosysalvos.coincidencias.dto.CoincidenciasDTO;
import com.sanosysalvos.coincidencias.factory.ICoincidenciaFactory;
import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.service.ICoincidenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coincidencias")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CoincidenciaController {

    private final ICoincidenciaService service;
    private final ICoincidenciaFactory factory;
    @PostMapping
    public ResponseEntity<CoincidenciasDTO> crear(@RequestBody CoincidenciasDTO dto) {
        Coincidencia c = service.procesarNuevaCoincidencia(dto.getPetId(), dto.getOrgId());
        return ResponseEntity.ok(factory.toDTO(c));
    }

    @GetMapping
    public ResponseEntity<List<CoincidenciasDTO>> listar() {
        List<CoincidenciasDTO> dtos = service.listarTodas().stream()
                .map(factory::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoincidenciasDTO> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(factory::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<CoincidenciasDTO> actualizar(@PathVariable Long id, @RequestParam String estado) {
        Coincidencia c = service.actualizarEstado(id, estado);
        return ResponseEntity.ok(factory.toDTO(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}