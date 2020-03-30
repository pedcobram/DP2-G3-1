/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.exceptions.DateException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.NumberOfPlayersAndCoachException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
import org.springframework.samples.petclinic.service.exceptions.StatusException;
import org.springframework.samples.petclinic.web.validators.FootballPlayerValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FootballPlayerController {

	private static final String			VIEWS_PLAYER_CREATE_OR_UPDATE_FORM	= "footballPlayers/createOrUpdateFootballPlayerForm";

	private final FootballPlayerService	footballPlayerService;

	private final FootballClubService	footballClubService;


	@Autowired
	public FootballPlayerController(final FootballPlayerService footballPlayerService, final FootballClubService footballClubService) {
		this.footballPlayerService = footballPlayerService;
		this.footballClubService = footballClubService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("footballPlayer")
	public void initFootballPlayerBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new FootballPlayerValidator());
	}

	@GetMapping(value = "/footballPlayers") //LISTA DE JUGADORES
	public String showFootballPlayerList(final Map<String, Object> model) {

		List<FootballPlayer> players = new ArrayList<>();
		players.addAll(this.footballPlayerService.findAllFootballPlayers());

		model.put("footballPlayers", players);

		return "footballPlayers/footballPlayerList";
	}

	@GetMapping(value = "/footballPlayers/freeAgent") //LISTA DE JUGADORES FA
	public String showFootballPlayerFAList(final Map<String, Object> model) {

		List<FootballPlayer> players = new ArrayList<>();
		players.addAll(this.footballPlayerService.findAllFootballPlayersFA());

		model.put("footballPlayers", players);

		return "footballPlayers/footballPlayerList";
	}

	@GetMapping(value = "/footballClubs/list/{footballClubId}/footballPlayers") //LISTA DE JUGADORES DE UN EQUIPO
	public String showFootballPlayerListByClub(final Map<String, Object> model, @PathVariable("footballClubId") final int footballClubId) {

		List<FootballPlayer> players = new ArrayList<>();
		players.addAll(this.footballPlayerService.findAllClubFootballPlayers(footballClubId));

		model.put("footballPlayers", players);

		return "footballPlayers/footballPlayerList";
	}

	@GetMapping(value = "/footballClubs/myClub/footballPlayers") //LISTA DE JUGADORES DE MI EQUIPO
	public String showPlayerListMyClub(final Map<String, Object> model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub == null) {  //SI NO TIENE EQUIPO MANDAMOS A QUE SE LO REGISTRE
			return "footballClubs/myClubEmpty";
		}

		List<FootballPlayer> players = new ArrayList<>();
		players.addAll(this.footballPlayerService.findAllClubFootballPlayers(footballClub.getId()));

		model.put("footballPlayers", players);
		model.put("thisClubPresidentUsername", footballClub.getPresident().getUser().getUsername());
		model.put("thisClubStatus", footballClub.getStatus());

		return "footballPlayers/footballPlayerList";
	}

	@GetMapping("/footballPlayers/{footballPlayerId}") //VISTA DELLADA DE JUGADOR POR ID
	public ModelAndView showFootballPlayer(@PathVariable("footballPlayerId") final int footballPlayerId) {

		ModelAndView mav = new ModelAndView("footballPlayers/footballPlayerDetails");
		mav.addObject(this.footballPlayerService.findFootballPlayerById(footballPlayerId));
		mav.addObject("footballPlayerAge", this.footballPlayerService.findFootballPlayerById(footballPlayerId).getAge());

		return mav;
	}

	@GetMapping(value = "/footballPlayer/new")  //REGISTRAR JUGADOR - GET
	public String initCreationForm(final Map<String, Object> model) throws CredentialException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		if (footballClub.getStatus() == true) {
			throw new CredentialException("Forbidden Access"); //SEGURIDAD
		}

		List<FootballPlayerPosition> positions = new ArrayList<FootballPlayerPosition>();
		positions.add(FootballPlayerPosition.DEFENDER);
		positions.add(FootballPlayerPosition.GOALKEEPER);
		positions.add(FootballPlayerPosition.MIDFIELDER);
		positions.add(FootballPlayerPosition.STRIKER);

		FootballPlayer footballPlayer = new FootballPlayer();
		footballPlayer.setValue(10000000);

		model.put("footballPlayer", footballPlayer);
		model.put("positions", positions);

		//Contract Fields

		Date moment = new Date(System.currentTimeMillis() - 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = simpleDateFormat.format(moment);

		Date date2 = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(Calendar.YEAR, +5);
		date2 = cal.getTime();
		String date3 = simpleDateFormat.format(date2);

		model.put("salary", "1.000.000,00 €");
		model.put("clause", "5.000.000,00 €");
		model.put("startDate", date);
		model.put("endDate", date3);

		return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/footballPlayer/new") //REGISTRAR JUGADOR - POST
	public String processCreationForm(@Valid final FootballPlayer footballPlayer, final BindingResult result, final Model model)
		throws DataAccessException, DuplicatedNameException, NumberOfPlayersAndCoachException, MoneyClubException, StatusException, DateException, SalaryException {

		//Contract Fields
		Date moment = new Date(System.currentTimeMillis() - 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String date = simpleDateFormat.format(moment);

		Date date2 = new Date(System.currentTimeMillis() - 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date2);
		cal.add(Calendar.YEAR, +2);
		date2 = cal.getTime();
		String date3 = simpleDateFormat.format(date2);

		model.addAttribute("salary", "1.000.000,00 €");
		model.addAttribute("clause", "5.000.000,00 €");
		model.addAttribute("startDate", date);
		model.addAttribute("endDate", date3);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		FootballClub thisClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		List<FootballPlayerPosition> positions = new ArrayList<FootballPlayerPosition>();
		positions.add(FootballPlayerPosition.DEFENDER);
		positions.add(FootballPlayerPosition.GOALKEEPER);
		positions.add(FootballPlayerPosition.MIDFIELDER);
		positions.add(FootballPlayerPosition.STRIKER);
		model.addAttribute("positions", positions);

		//Si hay errores seguimos en la vista de creación
		if (result.hasErrors()) {
			return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				ContractPlayer newContract = new ContractPlayer();
				newContract.setClause(5000000);
				newContract.setClub(thisClub);
				newContract.setPlayer(footballPlayer);
				newContract.setEndDate(date2);
				newContract.setStartDate(moment);
				newContract.setSalary(1000000);

				footballPlayer.setClub(thisClub);
				footballPlayer.setValue(10000000);

				this.footballPlayerService.saveFootballPlayer(footballPlayer, newContract);

			} catch (DateException ex) {
				result.rejectValue("birthDate", "code.error.validator.playerBirthDate", "at least 16 years old");
				return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
			} catch (DuplicatedNameException ex) {
				result.rejectValue("firstName", "duplicate", "already exists");
				result.rejectValue("lastName", "duplicate", "already exists");
				return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
			} catch (NumberOfPlayersAndCoachException ex) {
				result.rejectValue("position", "max7players");
				return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
			} catch (MoneyClubException ex) {
				result.rejectValue("position", "code.error.validator.salary", "Not enough money!");
				return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
			} catch (StatusException ex) {
				return "redirect:exceptions/forbidden";
			}

			return "redirect:/footballClubs/myClub/footballPlayers";
		}
	}
}
