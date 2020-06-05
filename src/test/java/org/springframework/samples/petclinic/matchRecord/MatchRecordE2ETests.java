
package org.springframework.samples.petclinic.matchRecord;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = Replace.ANY)
public class MatchRecordE2ETests {

	@Autowired
	private MockMvc mockMvc;


	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void initCreateMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/new")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitCreateMatchRequest() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/new")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void processCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/matchRecord/1/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("status", "NOT_PUBLISHED").param("season_start", "2019").param("season_end", "2020")
			.param("result", "Result")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.model().hasNoErrors()).andExpect(MockMvcResultMatchers.redirectedUrl("/myfootballClub/"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO NEGATIVO
	void dontProcessCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/matchRecord/1/new").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "").param("status", "").param("season_start", "a").param("season_end", "b").param("result", "Result"))
			.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasErrors()).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "season_start"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "season_end")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "title"))
			.andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("matchRecord", "status"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void initUpdateMatchRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/edit")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontInitUpdateMatchRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/edit")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void processUpdateMatchRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/matchRecord/1/edit").with(SecurityMockMvcRequestPostProcessors.csrf()).param("title", "Title").param("status", "NOT_PUBLISHED").param("season_start", "2019").param("season_end", "2020")
			.param("result", "Result")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().hasNoErrors());
	}

	@WithMockUser(username = "referee1", authorities = {
		"referee"
	})
	@Test //CASO NEGATIVO
	void dontUpdateMatchRecordForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/matches/matchRecord/1/edit")
			.with(SecurityMockMvcRequestPostProcessors.csrf())
			.param("title", "")
			.param("status", "")
			.param("season_start", "a")
			.param("season_end", "b")
			.param("result", "Result"))
			.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void viewMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/view")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO POSITIVO
	void dontViewMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/1/view")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/goal/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/goal/add/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/goal/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontSubstractGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/goal/substract/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addAssistMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/assist/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddAssistMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/assist/add/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractAssistMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/assist/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO POSITIVO
	void dontSubstractAssistMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/assist/substract/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addRedCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/redCard/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddRedCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/redCard/add/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractRedCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/redCard/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontSubstractRedCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/redCard/substract/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addYellowCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/yellowCard/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddYellowCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/yellowCard/add/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractYellowCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/yellowCard/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontSubstractYellowCardMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/yellowCard/substract/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void addReceivedGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/receivedGoals/add/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontAddReceivedGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/receivedGoals/add/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

	@WithMockUser(username = "rufus", authorities = {
		"referee"
	})
	@Test //CASO POSITIVO
	void substractReceivedGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/receivedGoals/substract/1/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithAnonymousUser
	@Test //CASO NEGATIVO
	void dontSubstractReceivedGoalMatchRecord() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/matches/matchRecord/receivedGoals/substract/1/1")).andExpect(MockMvcResultMatchers.status().is3xxRedirection()).andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
	}

}
