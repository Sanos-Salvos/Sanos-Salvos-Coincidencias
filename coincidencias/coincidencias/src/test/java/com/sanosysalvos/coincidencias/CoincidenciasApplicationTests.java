package com.sanosysalvos.coincidencias;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test") // Mantiene el contexto del test usando H2
class CoincidenciasApplicationTests {

	@Test
	void contextLoads() {
		// Valida que el contexto de Spring Boot levante bien en aislamiento
	}

	@Test
	void main_deberiaArrancarLaAplicacion() {
		// 💡 AL PASARLE ESTE ARGUMENTO, OBLIGAMOS AL MAIN A USAR H2 EN VEZ DE POSTGRES
		String[] args = {"--spring.profiles.active=test"};

		assertDoesNotThrow(() -> CoincidenciasApplication.main(args));
	}
}