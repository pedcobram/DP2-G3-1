
package org.springframework.samples.petclinic.service.rafa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.service.CalendaryService;
import org.springframework.samples.petclinic.service.CompetitionService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
//@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CalendaryServiceTests {

	@Autowired
	private CalendaryService	calendaryService;

	@Autowired
	private CompetitionService	competitionService;


	@Test //CASO POSITIVO - Buscar Calendario por Id
	void shouldFindCalendaryById() {

		Calendary calendary = this.calendaryService.findById(1);

		Assertions.assertTrue(calendary != null);

	}

	@Test //CASO NEGATIVO - Buscar Calendario por Id
	void shouldNotFindCalendaryById() {

		Calendary calendary = this.calendaryService.findById(99);

		Assertions.assertTrue(calendary == null);

	}

	@Test //CASO POSITIVO - Buscar Calendario por id de competición
	void shouldFindCalendaryByCompetitionId() {

		Calendary calendary = this.calendaryService.findCalendaryByCompetitionId(2);

		Assertions.assertTrue(calendary != null);
	}

	@Test //CASO NEGATIVO - Buscar Calendario por id de competición
	void shouldNotFindCalendaryByCompetitionId() {

		Calendary calendary = this.calendaryService.findCalendaryByCompetitionId(1);

		Assertions.assertFalse(calendary != null);
	}

	@Test //CASO POSITIVO - Guardar Calendario 
	void shouldSaveCalendary() throws DataAccessException {

		Calendary calendary = this.calendaryService.findById(2);

		Assertions.assertFalse(calendary != null); //Vemos que no existe

		calendary = new Calendary();

		Competition comp = this.competitionService.findCompetitionById(1);

		Calendary calendary2 = this.calendaryService.findCalendaryByCompetitionId(1);

		Assertions.assertFalse(calendary2 != null); //Vemos que no existe un calendario para la competición 1

		calendary.setCompetition(comp);

		this.calendaryService.saveCalendary(calendary);

		calendary = this.calendaryService.findCalendaryByCompetitionId(1);

		Assertions.assertTrue(calendary != null); //Ahora si existe 
	}

	@Test //CASO NEGATIVO - Guardar Calendario 
	void shouldNotSaveCalendaryWithoutCompetition() throws DataAccessException {

		Calendary c = new Calendary();

		Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			this.calendaryService.saveCalendary(c);
		});
	}

	@Test //CASO POSITIVO - Borrar Calendario (No hay caso negativo)
	void shouldDeleteCalendary() throws DataAccessException {

		Calendary c = this.calendaryService.findById(1);

		Assertions.assertTrue(c != null);

		this.calendaryService.deleteCalendary(c);

		c = this.calendaryService.findById(1);

		Assertions.assertFalse(c != null);
	}

}
