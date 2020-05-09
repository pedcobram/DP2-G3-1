
package org.springframework.samples.petclinic.web.rafa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Calendary;
import org.springframework.samples.petclinic.model.Competition;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.samples.petclinic.model.Enum.MatchStatus;
import org.springframework.samples.petclinic.service.CalendaryService;
import org.springframework.samples.petclinic.service.CompetitionService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerMatchStatisticService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.JornadaService;
import org.springframework.samples.petclinic.service.MatchRecordService;
import org.springframework.samples.petclinic.service.MatchService;
import org.springframework.samples.petclinic.service.RefereeService;
import org.springframework.samples.petclinic.web.CompetitionController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = CompetitionController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
public class CompetitionControllerTest {

	private static final int					TEST_COMPETITION_ID	= 1;

	@Autowired
	private MockMvc								mockMvc;

	@MockBean
	private CompetitionService					competitionService;

	@MockBean
	private FootballClubService					footballClubService;

	@MockBean
	private FootballPlayerService				footballPlayerService;

	@MockBean
	private RefereeService						refereeService;

	@MockBean
	private MatchService						matchService;

	@MockBean
	private FootballPlayerMatchStatisticService	footballPlayerMatchStatisticService;

	@MockBean
	private MatchRecordService					matchRecordService;

	@MockBean
	private JornadaService						jornadaService;

	@MockBean
	private CalendaryService					calendaryService;

	private Competition							midgarTournament;


	@BeforeEach
	void setup() {

		User user = new User();
		user.setUsername("rufus");
		user.setPassword("shinra");
		user.setEnabled(true);

		CompetitionAdmin rufus = new CompetitionAdmin();
		rufus.setId(98);
		rufus.setFirstName("Rufus");
		rufus.setLastName("Shinra");
		rufus.setDni("12345678H");
		rufus.setEmail("rufus@shinra.com");
		rufus.setTelephone("608551023");
		rufus.setUser(user);

		this.midgarTournament = new Competition();

		List<String> clubs = new ArrayList<>();

		this.midgarTournament.setCreator("rufus");
		this.midgarTournament.setDescription("Tournament of Midgar");
		this.midgarTournament.setName("Midgar League");
		this.midgarTournament.setReward(50000000);
		this.midgarTournament.setStatus(false);
		this.midgarTournament.setType(CompetitionType.LEAGUE);
		this.midgarTournament.setId(CompetitionControllerTest.TEST_COMPETITION_ID);
		this.midgarTournament.setClubs(clubs);

		Calendary calendary = new Calendary();

		calendary.setCompetition(this.midgarTournament);
		calendary.setId(1);

		Collection<Jornada> jornadas = new HashSet<>();

		Jornada j = new Jornada();

		j.setCalendary(calendary);
		j.setId(1);
		j.setName("Jornada1");

		jornadas.add(j);

		Match match = new Match();

		Calendar cal = new GregorianCalendar();
		cal.set(2020, 03, 24, 20, 30);
		Date date = cal.getTime();

		match.setId(1);
		match.setTitle("Title");
		match.setStadium("Stadium");
		match.setMatchStatus(MatchStatus.TO_BE_PLAYED);
		match.setMatchDate(date);
		match.setCreator("rufus");
		
		List<Match> matches = new ArrayList<>();
		matches.add(match);
		
		Competition midgarTournament2 = new Competition();

		List<String> clubs2 = new ArrayList<>();

		midgarTournament2.setCreator("rufus");
		midgarTournament2.setDescription("Tournament of Midgar");
		midgarTournament2.setName("Midgar League2");
		midgarTournament2.setReward(50000000);
		midgarTournament2.setStatus(true);
		midgarTournament2.setType(CompetitionType.LEAGUE);
		midgarTournament2.setId(CompetitionControllerTest.TEST_COMPETITION_ID + 1);
		midgarTournament2.setClubs(clubs2);
		
		List<Competition> comps = new ArrayList<>();	
		comps.add(midgarTournament2);
		
		List<Competition> comps2 = new ArrayList<>();
		comps2.add(midgarTournament);
		comps2.add(midgarTournament2);

		BDDMockito.given(this.competitionService.findCompetitionById(CompetitionControllerTest.TEST_COMPETITION_ID)).willReturn(this.midgarTournament);
		BDDMockito.given(this.calendaryService.findCalendaryByCompetitionId(CompetitionControllerTest.TEST_COMPETITION_ID)).willReturn(calendary);
		BDDMockito.given(this.jornadaService.findAllJornadasFromCompetitionId(CompetitionControllerTest.TEST_COMPETITION_ID)).willReturn(jornadas);
		BDDMockito.given(this.jornadaService.findJornadaById(1)).willReturn(j);
		BDDMockito.given(this.competitionService.findAllMatchByJornadaId(1)).willReturn(matches);
		BDDMockito.given(this.matchService.findMatchById(1)).willReturn(match);
		BDDMockito.given(this.competitionService.findAllPublishedCompetitions()).willReturn(comps);
		BDDMockito.given(this.competitionService.findMyCompetitions("rufus")).willReturn(comps2);
		

	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VISTA DETALLADA DE COMPETICIÓN
	void testShowCompetition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competition")).andExpect(MockMvcResultMatchers.model().attributeExists("size"))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("name", Matchers.is("Midgar League"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("description", Matchers.is("Tournament of Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("type", Matchers.is(CompetitionType.LEAGUE))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("reward", Matchers.is(50000000)))).andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("creator", Matchers.is("rufus"))))
			.andExpect(MockMvcResultMatchers.view().name("competitions/competitionDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/competitionDetails.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - VISTA DE COMPETICIÓN si no se es el creador y no está publicado
	void testShowCompetitionViewError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("competition")).andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("size")).andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE COMPETICIÓN sin user
	void testShowCompetitionError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VISTA DETALLADA DE CALENDARIO
	void testShowCalendary() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("calendary")).andExpect(MockMvcResultMatchers.model().attributeExists("jornadas")).andExpect(MockMvcResultMatchers.view().name("competitions/calendaryDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/calendaryDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE CALENDARIO sin user
	void testShowCalendaryError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VISTA DETALLADA DE JORNADA
	void testShowJornada() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}", CompetitionControllerTest.TEST_COMPETITION_ID, 1))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("jornada"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("partidos"))
			.andExpect(MockMvcResultMatchers.model().attribute("partidos", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.view().name("competitions/jornadasDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/jornadasDetails.jsp"));
	}
	
	@Test //CASO NEGATIVO - VISTA DE JORNADA sin User
	void testShowJornadaError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}", CompetitionControllerTest.TEST_COMPETITION_ID, 1))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - VISTA DETALLADA DE PARTIDO
	void testShowMatch() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}/match/{matchId}", CompetitionControllerTest.TEST_COMPETITION_ID, 1, 1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("match")).andExpect(MockMvcResultMatchers.model().attribute("match", Matchers.hasProperty("title", Matchers.is("Title"))))
			.andExpect(MockMvcResultMatchers.model().attribute("match", Matchers.hasProperty("stadium", Matchers.is("Stadium")))).andExpect(MockMvcResultMatchers.view().name("competitions/matchDetails"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/matchDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE PARTIDO sin User
	void testShowMatchError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}/match/{matchId}", CompetitionControllerTest.TEST_COMPETITION_ID, 1, 1))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE COMPETICIONES PUBLICADAS
	void testShowCompetitionsPublishedList() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/list"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competitions"))
			.andExpect(MockMvcResultMatchers.model().attribute("competitions", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.view().name("competitions/competitionList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/competitionList.jsp"));
	}
	
	@Test //CASO NEGATIVO - VISTA DE COMPETICIONES PUBLICADAS sin User
	void testShowCompetitionsPublishedError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/list"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE MIS COMPETICIONES
	void testShowMyCompetitionsList() throws Exception {
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/mylist"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competitions"))
			.andExpect(MockMvcResultMatchers.model().attribute("competitions", Matchers.hasSize(2)))
			.andExpect(MockMvcResultMatchers.view().name("competitions/competitionList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/competitionList.jsp"));
	}
	
	@Test //CASO NEGATIVO - VISTA DE MIS COMPETICIONES sin User
	void testShowMyCompetitionsError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/mylist"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - CREAR COMPETICIÓN
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/new"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("isNew"))
			.andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}
	
	@Test //CASO NEGATIVO - GET - CREAR COMPETICIÓN sin user
	void testInitCreationFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/new"))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - POST - CREAR COMPETICIÓN
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/new")
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("name", "The League")
			.param("description", "Description")
			.param("type", "LEAGUE")
			.param("status", "false")
			.param("creator", "rufus")
			.param("reward", "50000000"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/null"));
	}
	
	@WithMockUser(username = "rufus")
	
	@Test //CASO NEGATIVO - POST - CREAR COMPETICIÓN
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/competition/new")
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.param("description", "Description")
				.param("type", "LEAGUE")
				.param("status", "false")
				.param("creator", "rufus")
				.param("reward", "50000000"))
				.andExpect(MockMvcResultMatchers.model().attributeHasErrors("competition"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - EDITAR COMPETICIÓN
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", CompetitionControllerTest.TEST_COMPETITION_ID))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("isEditing"))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("name", Matchers.is("Midgar League"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("description", Matchers.is("Tournament of Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("creator", Matchers.is("rufus"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("reward", Matchers.is(50000000))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("status", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("type", Matchers.is(CompetitionType.LEAGUE))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("status", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}
}
