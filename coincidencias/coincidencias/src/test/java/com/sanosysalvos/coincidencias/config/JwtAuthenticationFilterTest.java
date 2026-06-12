package com.sanosysalvos.coincidencias.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @InjectMocks
    private JwtAuthenticationFilter filter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    void conBearerToken_deberiaSetearAutenticacion() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer micontenido.jwt.token");

        filter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("usuario_autenticado", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    void sinAuthHeader_noDeberiaSetearAutenticacion() throws ServletException, IOException {
        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void conAuthHeaderSinBearer_noDeberiaSetearAutenticacion() throws ServletException, IOException {
        request.addHeader("Authorization", "Basic dXNlcjpwYXNz");

        filter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void siempreDeberiaLlamarFilterChain() throws ServletException, IOException {
        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void conBearerToken_deberiaLlamarFilterChain() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer token123");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void conBearerVacio_deberiaSetearAutenticacion() throws ServletException, IOException {
        request.addHeader("Authorization", "Bearer ");

        filter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
