package com.sanosysalvos.coincidencias.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class WebConfigTest {

    @Test
    void addCorsMappings_deberiaConfigurarCors() {
        WebConfig config = new WebConfig();
        CorsRegistry registry = mock(CorsRegistry.class);
        CorsRegistration registration = mock(CorsRegistration.class, RETURNS_SELF);

        when(registry.addMapping(anyString())).thenReturn(registration);

        config.addCorsMappings(registry);

        verify(registry).addMapping("/**");
        verify(registration).allowedOrigins("http://localhost:3000", "http://localhost:5173");
    }
}
