package com.sanosysalvos.coincidencias.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "coincidencias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coincidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long petId;
    private Long organizacionId;
    private String estado; // PENDIENTE, ENCONTRADO
    private LocalDateTime fechaCreacion;
}