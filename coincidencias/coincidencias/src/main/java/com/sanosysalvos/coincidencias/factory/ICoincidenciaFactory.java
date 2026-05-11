package com.sanosysalvos.coincidencias.factory;

import com.sanosysalvos.coincidencias.model.Coincidencia;

public interface ICoincidenciaFactory {
    Coincidencia crear(Long petId, Long orgId);
}