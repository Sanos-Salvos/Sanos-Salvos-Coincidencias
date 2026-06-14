package com.sanosysalvos.coincidencias.factory;

import com.sanosysalvos.coincidencias.dto.CoincidenciasDTO;
import com.sanosysalvos.coincidencias.model.Coincidencia;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class CoincidenciaFactoryImpl implements ICoincidenciaFactory {

    @Override
    public Coincidencia crear(Long petId, Long orgId) {
        Coincidencia c = new Coincidencia();
        c.setPetId(petId);
        c.setOrganizacionId(orgId);
        c.setEstado("PENDIENTE");
        c.setFechaCreacion(LocalDateTime.now());
        return c;
    }

    @Override
    public CoincidenciasDTO toDTO(Coincidencia coincidencia) {
        if (coincidencia == null) {
            return null;
        }

        CoincidenciasDTO dto = new CoincidenciasDTO();
        dto.setId(coincidencia.getId());
        dto.setPetId(coincidencia.getPetId());
        dto.setOrgId(coincidencia.getOrganizacionId());
        dto.setEstado(coincidencia.getEstado());
        dto.setMensaje("Procesado correctamente");

        return dto;
    }
}