package com.sanosysalvos.coincidencias.config;

import com.sanosysalvos.coincidencias.service.ICoincidenciaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CoincidenciasConsumer {

    private final ICoincidenciaService service;

    public CoincidenciasConsumer(ICoincidenciaService service) {
        this.service = service;
    }
    @KafkaListener(topics = "pet-topic", groupId = "coincidencias-group")
    public void escucharMascotas(String message) {
        System.out.println("Evento recibido desde PET: " + message);
        service.procesarNuevaCoincidencia(1L, 1L);
    }

    @KafkaListener(topics = "organizaciones-topic", groupId = "coincidencias-group")
    public void escucharOrganizaciones(String message) {
        System.out.println("===============================================");
        System.out.println("¡NUEVO EVENTO RECIBIDO DESDE ORGANIZACIONES!");
        System.out.println("Mensaje: " + message);
        System.out.println("===============================================");

    }
}