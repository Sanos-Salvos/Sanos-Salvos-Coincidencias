package com.sanosysalvos.coincidencias.config;

import com.sanosysalvos.coincidencias.service.ICoincidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerConfig {

    @Autowired
    private ICoincidenciaService service;

    @KafkaListener(topics = "pet-topic", groupId = "coincidencias-group")
    public void escucharMascotas(String message) {
        System.out.println("Evento recibido desde PET: " + message);
        service.procesarNuevaCoincidencia(1L, 1L);
    }
}