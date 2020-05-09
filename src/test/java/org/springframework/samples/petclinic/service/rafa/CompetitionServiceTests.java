
package org.springframework.samples.petclinic.service.rafa;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.samples.petclinic.service.CompetitionService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.NotEnoughMoneyException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CompetitionServiceTests {

	@Autowired
	private CompetitionService competitionService;


	@Test //CASO POSITIVO - Buscar Competición por Id
	void shouldFindCompetitionById() {

		Competition competition = this.competitionService.findCompetitionById(1);

		Assertions.assertTrue(competition != null);

	}

	@Test //CASO NEGATIVO - Buscar Competición por Id
	void shouldNotFindCompetitionById() {

		Competition competition = this.competitionService.findCompetitionById(99);

		Assertions.assertTrue(competition == null);

	}

	@Test //CASO POSITIVO - Buscar Todas las Competiciones Publicadas
	void shouldFindAllPublishedCompetition() {

		List<Competition> comps = new ArrayList<>();

		comps.addAll(this.competitionService.findAllPublishedCompetitions());

		int count = comps.size();

		Assertions.assertTrue(count == 1);
	}

	@Test //CASO POSITIVO - Buscar Todos los partidos por jornada Id
	void shouldFindAllMatchByJornadaId() {

		List<Match> matches = new ArrayList<>();

		matches.addAll(this.competitionService.findAllMatchByJornadaId(1));

		int count = matches.size();

		Assertions.assertTrue(count == 1);
	}

	@Test //CASO POSITIVO - Buscar Todas las Competiciones por username
	void shouldFindAllMYCompetition() {

		List<Competition> comps = new ArrayList<>();

		comps.addAll(this.competitionService.findMyCompetitions("pedro"));

		int count = comps.size();

		Assertions.assertTrue(count == 2);
	}

	@Test //CASO POSITIVO - Buscar Equipos por competición Id
	void shouldFindAllClubsByCompetitionId() { //Busca los equipos publicos que NO están en la competición, para poder añadirlos a ésta

		List<String> clubs = new ArrayList<>();

		clubs.addAll(this.competitionService.findClubsById(1));

		int count = clubs.size();

		Assertions.assertTrue(count == 5);
	}

	@Test //CASO POSITIVO - Guardar Competición
	void shouldSaveCompetition() throws DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException {

		Competition comp = this.competitionService.findCompetitionById(3);

		Assertions.assertFalse(comp != null); //Vemos que no existe

		comp = new Competition();

		comp.setName("Competition 1");
		comp.setDescription("Description 1");
		comp.setCreator("pedro");
		comp.setReward(5000000);
		comp.setType(CompetitionType.LEAGUE);
		comp.setStatus(false);

		this.competitionService.saveCompetition(comp);

		comp = this.competitionService.findCompetitionById(3);

		Assertions.assertTrue(comp != null); //Ahora si existe 
	}

	@Test //CASO NEGATIVO - Guardar Competición con valores nulos
	void shouldNotSaveCompetitionWithNullData() throws DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException {

		Competition comp = new Competition();

		comp.setName(null);
		comp.setDescription("Description 1");
		comp.setCreator("pedro");
		comp.setReward(5000000);
		comp.setType(CompetitionType.LEAGUE);
		comp.setStatus(false);

		Assertions.assertThrows(NullPointerException.class, () -> {
			this.competitionService.saveCompetition(comp);
		});
	}

	@Test //CASO NEGATIVO - Guardar Competición con valores en blanco
	void shouldNotSaveCompetitionWhitEmptyData() throws DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException {

		Competition comp = new Competition();

		comp.setName("");
		comp.setDescription("Description 1");
		comp.setCreator("pedro");
		comp.setReward(5000000);
		comp.setType(CompetitionType.LEAGUE);
		comp.setStatus(false);

		Assertions.assertThrows(ConstraintViolationException.class, () -> {
			this.competitionService.saveCompetition(comp);
		});
	}

	@Test //CASO NEGATIVO - Guardar Competición //RN: Nombre Duplicado
	void shouldThrowDuplicatedNameException() throws DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException {

		Competition comp = new Competition();

		comp.setName("Premier League");
		comp.setDescription("Description 1");
		comp.setCreator("pedro");
		comp.setReward(5000000);
		comp.setType(CompetitionType.LEAGUE);
		comp.setStatus(false);

		Assertions.assertThrows(DuplicatedNameException.class, () -> {
			this.competitionService.saveCompetition(comp);
		});
	}

	@Test //CASO NEGATIVO - Guardar Competición //RN: La recompensa mínima deberá de ser de 5.000.000 €
	void shouldThrowMoneyException() throws DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException {

		Competition comp = new Competition();

		comp.setName("Liga 1");
		comp.setDescription("Description 1");
		comp.setCreator("pedro");
		comp.setReward(1000); //Dinero por debajo de 50mill
		comp.setType(CompetitionType.LEAGUE);
		comp.setStatus(false);

		Assertions.assertThrows(NotEnoughMoneyException.class, () -> {
			this.competitionService.saveCompetition(comp);
		});
	}

	@Test //CASO NEGATIVO - Guardar Competición A la hora de publicar: Las ligas solo podrán ser de número Par y de 4 equipos en adelante (4, 6, 8, 10, etc...)
	void shouldThrowStatusException() throws DataAccessException, DuplicatedNameException, NotEnoughMoneyException, StatusException {

		Competition comp = this.competitionService.findCompetitionById(1);

		Assertions.assertTrue(comp.getClubs().size() == 4); //Vemos que tiene cuatro equipos

		comp.getClubs().remove(0); //Quitamos el primero

		Assertions.assertTrue(comp.getClubs().size() == 3); //Vemos que tiene tres equipos

		comp.setStatus(true); //Ponemos el club en público

		Assertions.assertThrows(StatusException.class, () -> {
			this.competitionService.saveCompetition(comp);
		});
	}

	@Test //CASO POSITIVO - Borrar Competición
	void shouldDeleteCompetition() throws DataAccessException, StatusException {

		Competition comp = this.competitionService.findCompetitionById(1);

		Assertions.assertTrue(comp != null); //vemos que existe

		this.competitionService.deleteCompetition(comp);

		comp = this.competitionService.findCompetitionById(1);

		Assertions.assertFalse(comp != null); //Ya no existe
	}

	@Test //CASO NEGATIVO- Borrar Competición //RN: Si se ha disputado algun partido de la competición ya no se podrá borrar.
	void shouldThrowStatusExceptionDeleting() throws DataAccessException, StatusException {

		Competition comp = this.competitionService.findCompetitionById(2);

		Assertions.assertThrows(StatusException.class, () -> {
			this.competitionService.deleteCompetition(comp);
		});
	}

}
