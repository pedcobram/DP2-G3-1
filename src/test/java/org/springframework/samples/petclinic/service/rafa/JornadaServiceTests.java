
package org.springframework.samples.petclinic.service.rafa;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.service.CalendaryService;
import org.springframework.samples.petclinic.service.JornadaService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JornadaServiceTests {

	@Autowired
	private JornadaService		jornadaService;

	@Autowired
	private CalendaryService	calendaryService;


	@Test //CASO POSITIVO - Buscar Jornada por Id
	void shouldFindJornadasById() {

		Jornada jornada = this.jornadaService.findJornadaById(1);

		Assertions.assertTrue(jornada != null);

	}

	@Test //CASO NEGATIVO - Buscar Jornada por Id
	void shouldNotFindJornadasById() {

		Jornada jornada = this.jornadaService.findJornadaById(99);

		Assertions.assertTrue(jornada == null);

	}

	@Test //CASO POSITIVO - Buscar Todas las jornadas por ID de competición
	void shouldFindAllJornadasByCompetitionId() {

		List<Jornada> jornadas = new ArrayList<>();

		jornadas.addAll(this.jornadaService.findAllJornadasFromCompetitionId(2));

		int count = jornadas.size();

		Assertions.assertTrue(count == 6);
	}

	@Test //CASO POSITIVO - Guardar Jornada 
	void shouldSaveJornada() throws DataAccessException {

		Jornada jornada = this.jornadaService.findJornadaById(7);

		Assertions.assertFalse(jornada != null); //Vemos que no existe la Jornada

		jornada = new Jornada();

		Calendary calendary = this.calendaryService.findById(1);

		jornada.setName("Jornada 7");
		jornada.setCalendary(calendary);

		this.jornadaService.saveJornada(jornada);

		jornada = this.jornadaService.findJornadaById(7);

		Assertions.assertTrue(jornada != null); //Ahora si existe la jornada
	}

	@Test //CASO NEGATIVO - Guardar Jornada 
	void shouldNotSaveJornadaWithoutACalendary() throws DataAccessException {

		Jornada j = this.jornadaService.findJornadaById(7);

		Assertions.assertFalse(j != null); //Vemos que no existe la Jornada

		Jornada jornada = new Jornada();

		jornada.setName("Jornada 7");

		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			this.jornadaService.saveJornada(jornada);
		});

	}

	@Test //CASO POSITIVO - Borrar Jornada (No hay caso negativo)
	void shouldDeleteJornada() throws DataAccessException {

		Jornada jornada = this.jornadaService.findJornadaById(1);

		Assertions.assertTrue(jornada != null);

		this.jornadaService.deleteJornada(jornada);

		jornada = this.jornadaService.findJornadaById(1);

		Assertions.assertFalse(jornada != null);
	}

}
