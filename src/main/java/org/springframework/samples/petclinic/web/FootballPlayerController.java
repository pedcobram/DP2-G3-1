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
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.samples.petclinic.service.FootballClubService;
import org.springframework.samples.petclinic.service.FootballPlayerService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FootballPlayerController {

	private static final String			VIEWS_PLAYER_CREATE_OR_UPDATE_FORM	= "footballPlayers/createOrUpdateFootballPlayerForm";

	@Autowired
	private final FootballPlayerService	footballPlayerService;

	@Autowired
	private final FootballClubService	footballClubService;

	@Autowired
	private final ContractService		contractService;


	@Autowired
	public FootballPlayerController(final FootballPlayerService footballPlayerService, final FootballClubService footballClubService, final ContractService contractService) {
		this.footballPlayerService = footballPlayerService;
		this.footballClubService = footballClubService;
		this.contractService = contractService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("footballPlayer")
	public void initFootballPlayerBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new FootballPlayerValidator());
	}

	//Vista de la lista de jugadores
	@GetMapping(value = "/footballPlayers")
	public String showFootballPlayerList(final Map<String, Object> model) {

		//Creamos una colección de jugadores
		List<FootballPlayer> players = new ArrayList<>();

		//La llenamos con todos los equipos de la db
		players.addAll(this.footballPlayerService.findAllFootballPlayers());

		//Ponemos en el modelo la colección de equipos
		model.put("footballPlayers", players);

		//Mandamos a la vista de listado de equipos
		return "footballPlayers/footballPlayerList";
	}

	//Esto es para generar el xml de la lista de equipos
	@GetMapping(value = "/footballPlayers.xml")
	public @ResponseBody Collection<FootballPlayer> showResourcesFootballPlayerList() {

		Collection<FootballPlayer> players = new HashSet<FootballPlayer>();
		players.addAll(this.footballPlayerService.findAllFootballPlayers());

		return players;
	}

	//Vista de la lista de jugadores FA
	@GetMapping(value = "/footballPlayers/freeAgent")
	public String showFootballPlayerFAList(final Map<String, Object> model) {

		//Creamos una colección de jugadores
		List<FootballPlayer> players = new ArrayList<>();

		//La llenamos con todos los equipos de la db
		players.addAll(this.footballPlayerService.findAllFootballPlayersFA());

		//Ponemos en el modelo la colección de equipos
		model.put("footballPlayers", players);

		//Mandamos a la vista de listado de equipos
		return "footballPlayers/footballPlayerList";
	}

	//Vista de la lista de jugadores por equipos
	@GetMapping(value = "/footballClub/{footballClubId}/footballPlayers")
	public String showFootballPlayerListByClub(final Map<String, Object> model, @PathVariable("footballClubId") final int footballClubId) {

		//Creamos una colección de jugadores
		List<FootballPlayer> players = new ArrayList<>();

		//La llenamos con todos los equipos de la db
		players.addAll(this.footballPlayerService.findAllClubFootballPlayers(footballClubId));

		//Ponemos en el modelo la colección de equipos
		model.put("footballPlayers", players);

		//Mandamos a la vista de listado de equipos
		return "footballPlayers/footballPlayerList";
	}

	//Vista de Jugadores por Id - Authenticateds
	@GetMapping("/footballPlayers/{footballPlayerId}")
	public ModelAndView showFootballPlayer(@PathVariable("footballPlayerId") final int footballPlayerId) {
		//Creamos la vista de equipo con la url del archivo.jsp de la vista
		ModelAndView mav = new ModelAndView("footballPlayers/footballPlayerDetails");

		//Añadimos los datos del equipo según la id de la url
		mav.addObject(this.footballPlayerService.findFootballPlayerById(footballPlayerId));

		mav.addObject("footballPlayerAge", this.footballPlayerService.findFootballPlayerById(footballPlayerId).getAge());
		//devolvemos la vista con los datos del equipo en cuestión
		return mav;
	}

	//Crear Jugador - Get

	@GetMapping(value = "/footballPlayer/new")
	public String initCreationForm(final Map<String, Object> model) {

		FootballPlayer footballPlayer = new FootballPlayer();
		List<FootballPlayerPosition> positions = new ArrayList<FootballPlayerPosition>();

		footballPlayer.setValue(10000000);

		positions.add(FootballPlayerPosition.DEFENDER);
		positions.add(FootballPlayerPosition.GOALKEEPER);
		positions.add(FootballPlayerPosition.MIDFIELDER);
		positions.add(FootballPlayerPosition.STRIKER);

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

	//Crear Jugador - Post
	@PostMapping(value = "/footballPlayer/new")
	public String processCreationForm(@Valid final FootballPlayer footballPlayer, final BindingResult result, final Model model) throws DataAccessException, DuplicatedNameException {

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

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Obtenemos el Club del user actual
		FootballClub thisClub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		List<FootballPlayerPosition> positions = new ArrayList<FootballPlayerPosition>();
		positions.add(FootballPlayerPosition.DEFENDER);
		positions.add(FootballPlayerPosition.GOALKEEPER);
		positions.add(FootballPlayerPosition.MIDFIELDER);
		positions.add(FootballPlayerPosition.STRIKER);
		model.addAttribute("positions", positions);

		//Validación número de jugadores

		Collection<FootballPlayer> cp = this.footballPlayerService.findAllClubFootballPlayers(thisClub.getId());

		if (cp.size() >= 7) {
			result.rejectValue("position", "max7players");
			return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
		}

		//Si hay errores seguimos en la vista de creación
		if (result.hasErrors()) {
			return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
		} else {
			try {

				//Añadimos como club el del user actual
				footballPlayer.setClub(thisClub);
				//Ponemos el de valor a 10 millones (el valor inicial siempre será de 10mill)
				footballPlayer.setValue(10000000);

				//Guardamos el equipo en la db
				this.footballPlayerService.saveFootballPlayer(footballPlayer);

				//Generamos el contrato
				ContractPlayer newContract = new ContractPlayer();

				newContract.setClause(5000000);
				newContract.setClub(thisClub);
				newContract.setPlayer(footballPlayer);
				newContract.setEndDate(date2);
				newContract.setStartDate(moment);
				newContract.setSalary(1000000);

				this.contractService.saveContractPlayer(newContract);

				//Si capturamos excepción de nombre duplicado seguimos en la vista de creación
			} catch (DuplicatedNameException ex) {
				//Mostramos el mensaje de error en el atributo name
				result.rejectValue("firstName", "duplicate", "already exists");
				return FootballPlayerController.VIEWS_PLAYER_CREATE_OR_UPDATE_FORM;
			}
			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/footballClub/" + thisClub.getId() + "/footballPlayers?presidentUsername=" + currentPrincipalName;
		}
	}
}
