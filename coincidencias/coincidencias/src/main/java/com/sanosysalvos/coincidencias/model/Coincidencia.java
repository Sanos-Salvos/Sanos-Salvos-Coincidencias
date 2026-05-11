package com.sanosysalvos.coincidencias.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coincidencias")
public class Coincidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long petId;
    private Long organizacionId;
    private String estado; // PENDIENTE, ENCONTRADO
    private LocalDateTime fechaCreacion;

    public Coincidencia() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }
    public Long getOrganizacionId() { return organizacionId; }
    public void setOrganizacionId(Long organizacionId) { this.organizacionId = organizacionId; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}