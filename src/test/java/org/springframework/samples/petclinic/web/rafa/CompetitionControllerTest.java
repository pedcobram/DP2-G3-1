
package org.springframework.samples.petclinic.web.rafa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import org.assertj.core.util.Lists;
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
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.Jornada;
import org.springframework.samples.petclinic.model.Match;
import org.springframework.samples.petclinic.model.President;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Enum.CompetitionType;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
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

		User user2 = new User();
		user2.setUsername("rufus2");
		user2.setPassword("shinra");
		user2.setEnabled(true);

		Referee rufus2 = new Referee();
		rufus2.setId(1);
		rufus2.setFirstName("Rufus2");
		rufus2.setLastName("Shinra");
		rufus2.setDni("12345672H");
		rufus2.setEmail("rufus2@shinra.com");
		rufus2.setTelephone("608551022");
		rufus2.setUser(user2);

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

		String a = "Club1";
		String b = "Club2";
		String c = "Club3";
		String d = "Club4";
		String e = "Club5";

		FootballClub Club1 = new FootballClub();
		Club1.setName("Club1");
		Club1.setId(1);
		Club1.setStadium("st");		
		FootballPlayer player1 = new FootballPlayer();

		
		FootballClub Club3 = new FootballClub();
		Club3.setName("Club3");
		Club3.setId(3);
		Club3.setStadium("st");	
		FootballPlayer player3 = new FootballPlayer();

		
		FootballClub Club4 = new FootballClub();
		Club4.setName("Club4");
		Club4.setId(4);
		Club4.setStadium("st");
		FootballPlayer player4 = new FootballPlayer();
	
		
		FootballClub Club5 = new FootballClub();
		Club5.setName("Club5");
		Club5.setId(5);
		Club5.setStadium("st");	
		FootballPlayer player5 = new FootballPlayer();

		clubs.add(a);
		clubs.add(c);
		clubs.add(d);
		clubs.add(e);

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
		
		Jornada j2 = new Jornada();
		j2.setCalendary(calendary);
		j2.setId(2);
		j2.setName("Jornada2");
		jornadas.add(j2);
		
		Jornada j3 = new Jornada();
		j3.setCalendary(calendary);
		j3.setId(3);
		j3.setName("Jornada3");
		jornadas.add(j3);
		
		Jornada j4 = new Jornada();
		j4.setCalendary(calendary);
		j4.setId(4);
		j4.setName("Jornada4");
		jornadas.add(j4);
		
		Jornada j5 = new Jornada();
		j5.setCalendary(calendary);
		j5.setId(5);
		j5.setName("Jornada5");
		jornadas.add(j5);
		
		Jornada j6 = new Jornada();
		j6.setCalendary(calendary);
		j6.setId(6);
		j6.setName("Jornada6");
		jornadas.add(j6);
		
		Match match = new Match();

		Calendar cal2 = new GregorianCalendar();
		cal2.set(2020, 03, 24, 20, 30);
		Date date = cal2.getTime();

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
		comps2.add(this.midgarTournament);
		comps2.add(midgarTournament2);

		BDDMockito.given(this.competitionService.findCompetitionById(CompetitionControllerTest.TEST_COMPETITION_ID)).willReturn(this.midgarTournament);
		BDDMockito.given(this.calendaryService.findCalendaryByCompetitionId(CompetitionControllerTest.TEST_COMPETITION_ID)).willReturn(calendary);
		BDDMockito.given(this.jornadaService.findAllJornadasFromCompetitionId(CompetitionControllerTest.TEST_COMPETITION_ID)).willReturn(jornadas);
		BDDMockito.given(this.jornadaService.findJornadaById(1)).willReturn(j);
		BDDMockito.given(this.competitionService.findAllMatchByJornadaId(1)).willReturn(matches);
		BDDMockito.given(this.matchService.findMatchById(1)).willReturn(match);
		BDDMockito.given(this.competitionService.findAllPublishedCompetitions()).willReturn(comps);
		BDDMockito.given(this.competitionService.findMyCompetitions("rufus")).willReturn(comps2);
		BDDMockito.given(this.competitionService.findClubsById(CompetitionControllerTest.TEST_COMPETITION_ID)).willReturn(Lists.newArrayList(b));
		BDDMockito.given(this.footballClubService.findFootballClubByName("Club1")).willReturn(Club1);
		BDDMockito.given(this.footballClubService.findFootballClubByName("Club3")).willReturn(Club3);
		BDDMockito.given(this.footballClubService.findFootballClubByName("Club4")).willReturn(Club4);
		BDDMockito.given(this.footballClubService.findFootballClubByName("Club5")).willReturn(Club5);
		BDDMockito.given(this.refereeService.findRefereeById(1)).willReturn(rufus2);
		BDDMockito.given(this.footballPlayerService.findAllClubFootballPlayers(1)).willReturn(Lists.newArrayList(player1));
		BDDMockito.given(this.footballPlayerService.findAllClubFootballPlayers(3)).willReturn(Lists.newArrayList(player3));
		BDDMockito.given(this.footballPlayerService.findAllClubFootballPlayers(4)).willReturn(Lists.newArrayList(player4));
		BDDMockito.given(this.footballPlayerService.findAllClubFootballPlayers(5)).willReturn(Lists.newArrayList(player5));

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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}", CompetitionControllerTest.TEST_COMPETITION_ID, 1)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("jornada")).andExpect(MockMvcResultMatchers.model().attributeExists("partidos")).andExpect(MockMvcResultMatchers.model().attribute("partidos", Matchers.hasSize(1)))
			.andExpect(MockMvcResultMatchers.view().name("competitions/jornadasDetails")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/jornadasDetails.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE JORNADA sin User
	void testShowJornadaError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}", CompetitionControllerTest.TEST_COMPETITION_ID, 1)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
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
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/calendary/jornada/{jornadaId}/match/{matchId}", CompetitionControllerTest.TEST_COMPETITION_ID, 1, 1)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE COMPETICIONES PUBLICADAS
	void testShowCompetitionsPublishedList() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/list")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competitions"))
			.andExpect(MockMvcResultMatchers.model().attribute("competitions", Matchers.hasSize(1))).andExpect(MockMvcResultMatchers.view().name("competitions/competitionList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/competitionList.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE COMPETICIONES PUBLICADAS sin User
	void testShowCompetitionsPublishedError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/list")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - LISTA DE MIS COMPETICIONES
	void testShowMyCompetitionsList() throws Exception {

		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/mylist")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competitions"))
			.andExpect(MockMvcResultMatchers.model().attribute("competitions", Matchers.hasSize(2))).andExpect(MockMvcResultMatchers.view().name("competitions/competitionList"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/competitionList.jsp"));
	}

	@Test //CASO NEGATIVO - VISTA DE MIS COMPETICIONES sin User
	void testShowMyCompetitionsError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/mylist")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - CREAR COMPETICIÓN
	void testInitCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("competition"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("isNew")).andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}

	@Test //CASO NEGATIVO - GET - CREAR COMPETICIÓN sin user
	void testInitCreationFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/new")).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - POST - CREAR COMPETICIÓN
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "The League").param("description", "Description").param("type", "LEAGUE").param("status", "false")
			.param("creator", "rufus").param("reward", "50000000")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/null"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - CREAR COMPETICIÓN
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post("/competition/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("description", "Description").param("type", "LEAGUE").param("status", "false").param("creator", "rufus").param("reward", "50000000"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("competition")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - EDITAR COMPETICIÓN
	void testInitUpdateForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competition")).andExpect(MockMvcResultMatchers.model().attributeExists("isEditing"))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("name", Matchers.is("Midgar League"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("description", Matchers.is("Tournament of Midgar"))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("creator", Matchers.is("rufus")))).andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("reward", Matchers.is(50000000))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("status", Matchers.is(false))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("type", Matchers.is(CompetitionType.LEAGUE))))
			.andExpect(MockMvcResultMatchers.model().attribute("competition", Matchers.hasProperty("status", Matchers.is(false)))).andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - GET - EDITAR COMPETICIÓN con otro user
	void testInitUpdateFormWhitAnotherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - GET - EDITAR COMPETICIÓN si ya está publicada
	void testInitUpdateFormWithPublishedComp() throws Exception {

		this.midgarTournament.setStatus(true);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - EDITAR COMPETICIÓN sin user
	void testInitUpdateFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/edit", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - POST - EDITAR COMPETICIÓN
	void testProcessUpdateFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/edit", CompetitionControllerTest.TEST_COMPETITION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "The League2").param("description", "Description2")
			.param("type", "LEAGUE").param("status", "false").param("creator", "rufus").param("reward", "50000002")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/1"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - POST - EDITAR COMPETICIÓN
	void testProcessUpdateFormHasErrors() throws Exception {
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/edit", CompetitionControllerTest.TEST_COMPETITION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("description", "Description2").param("type", "LEAGUE")
				.param("status", "false").param("creator", "rufus").param("reward", "50000002"))
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("competition")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.view().name("competitions/createOrUpdateCompetitionForm"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/createOrUpdateCompetitionForm.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - POST - EDITAR COMPETICIÓN con otro user
	void testProcessUpdateFormWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/edit", CompetitionControllerTest.TEST_COMPETITION_ID).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - AÑADIR EQUIPOS
	void testInitAddForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/addClubs", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competition")).andExpect(MockMvcResultMatchers.model().attributeExists("clubsName")).andExpect(MockMvcResultMatchers.model().attributeExists("isAdd"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("size")).andExpect(MockMvcResultMatchers.model().attribute("clubsName", Matchers.hasSize(1))).andExpect(MockMvcResultMatchers.view().name("competitions/listClubs"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/listClubs.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - GET - AÑADIR EQUIPOS con otro user
	void testProcessAddFormWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/addClubs", CompetitionControllerTest.TEST_COMPETITION_ID).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - AÑADIR EQUIPOS sin user
	void testInitAddFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/addClubs", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - POST - AÑADIR EQUIPOS
	void testProcessAddFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/addClubs", CompetitionControllerTest.TEST_COMPETITION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("clubs", "Club2"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/1"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - POST - AÑADIR EQUIPOS con otro Usuario
	void testProcessAddFormWithErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competition/{competitionId}/addClubs", CompetitionControllerTest.TEST_COMPETITION_ID).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - GET - VER EQUIPOS
	void testInitShowForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/clubs", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeExists("competition")).andExpect(MockMvcResultMatchers.model().attributeExists("clubsName")).andExpect(MockMvcResultMatchers.model().attributeExists("isAdd"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("size")).andExpect(MockMvcResultMatchers.model().attribute("clubsName", Matchers.hasSize(4))).andExpect(MockMvcResultMatchers.view().name("competitions/listClubs"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/competitions/listClubs.jsp"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - GET - VER EQUIPOS con otro user
	void testProcessShowFormWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/clubs", CompetitionControllerTest.TEST_COMPETITION_ID).with(SecurityMockMvcRequestPostProcessors.csrf())).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden")).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@Test //CASO NEGATIVO - GET - VER EQUIPOS sin user
	void testInitShowFormError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competitions/{competitionId}/clubs", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - POST - BORRAR EQUIPOS
	void testProcessRemoveFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competitions/{competitionId}/clubs", CompetitionControllerTest.TEST_COMPETITION_ID).with(SecurityMockMvcRequestPostProcessors.csrf()).param("clubs", "Club2"))
			.andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/1"));
	}

	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - POST - BORRAR EQUIPOS con otro Usuario
	void testProcessRemoveFormWithErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/competitions/{competitionId}/clubs", CompetitionControllerTest.TEST_COMPETITION_ID)
			.with(SecurityMockMvcRequestPostProcessors.csrf()))
		.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}

	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - PUBLICAR COMPETICIÓN
	void testPublishCompetition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/publish", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/competitions/1"));
	}
	
	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - PUBLICAR COMPETICIÓN con otro usuario
	void testPublishCompetitionWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/publish", CompetitionControllerTest.TEST_COMPETITION_ID))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO NEGATIVO - PUBLICAR COMPETICIÓN si ya esta publicada
	void testPublishCompetitionWithAlreadyPublished() throws Exception {
		
		this.midgarTournament.setStatus(true);
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/publish", CompetitionControllerTest.TEST_COMPETITION_ID))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}
	
	@Test //CASO NEGATIVO - GET - PUBLICAR COMPETICIÓN  sin user
	void testPublishCompetitionError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/publish", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@WithMockUser(username = "rufus")

	@Test //CASO POSITIVO - BORRAR COMPETICIÓN
	void testDeleteCompetition() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/delete", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
			.andExpect(MockMvcResultMatchers.view().name("redirect:/"));
	}
	
	@WithMockUser(username = "rufus2")

	@Test //CASO NEGATIVO - BORRAR COMPETICIÓN con otro usuario
	void testDeleteCompetitionWithOtherUser() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/delete", CompetitionControllerTest.TEST_COMPETITION_ID))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("exceptions/forbidden"))
			.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/jsp/exceptions/forbidden.jsp"));
	}
	
	@Test //CASO NEGATIVO - GET - BORRAR COMPETICIÓN  sin user
	void testDeleteCompetitionError() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/competition/{competitionId}/delete", CompetitionControllerTest.TEST_COMPETITION_ID)).andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

}
