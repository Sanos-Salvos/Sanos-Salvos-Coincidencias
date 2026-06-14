package com.sanosysalvos.coincidencias.factory;

import com.sanosysalvos.coincidencias.dto.CoincidenciasDTO;
import com.sanosysalvos.coincidencias.model.Coincidencia;

public interface ICoincidenciaFactory {
    Coincidencia crear(Long petId, Long orgId);
    CoincidenciasDTO toDTO(Coincidencia coincidencia);
}