package com.sanosysalvos.coincidencias.controller;

import com.sanosysalvos.coincidencias.dto.CoincidenciasDTO;
import com.sanosysalvos.coincidencias.model.Coincidencia;
import com.sanosysalvos.coincidencias.service.ICoincidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coincidencias")
public class CoincidenciaController {

    @Autowired
    private ICoincidenciaService service;

    @PostMapping
    public ResponseEntity<CoincidenciasDTO> crear(@RequestParam Long petId, @RequestParam Long orgId) {
        Coincidencia c = service.procesarNuevaCoincidencia(petId, orgId);
        return ResponseEntity.ok(new CoincidenciasDTO(c.getId(), c.getPetId(), "Registrada con éxito"));
    }


    @GetMapping
    public List<Coincidencia> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coincidencia> obtener(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    public Coincidencia actualizar(@PathVariable Long id, @RequestParam String estado) {
        return service.actualizarEstado(id, estado);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}