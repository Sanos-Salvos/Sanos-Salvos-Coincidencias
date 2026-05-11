package com.sanosysalvos.coincidencias.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoincidenciasDTO {
    private Long id;
    private Long petId;
    private String mensaje;
}