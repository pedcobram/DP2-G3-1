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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballClubs;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.President;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FootballClubController {

	private static final String			VIEWS_CLUB_CREATE_OR_UPDATE_FORM	= "footballClubs/createOrUpdateFootballClubForm";

	@Autowired
	private final FootballClubService	footballClubService;

	@Autowired
	private final FootballPlayerService	footballPlayerService;


	@Autowired
	public FootballClubController(final FootballClubService footballClubService, final FootballPlayerService footballPlayerService) {
		this.footballClubService = footballClubService;
		this.footballPlayerService = footballPlayerService;
	}

	@InitBinder("footballClub")
	public void initFootballClubBinder(final WebDataBinder dataBinder) {
		dataBinder.setValidator(new FootballClubValidator());
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("status")
	public List<Boolean> populateStatus() {

		List<Boolean> status = new ArrayList<>();

		status.add(true);
		status.add(false);

		return status;
	}

	//Vista de la lista de equipos
	@GetMapping(value = "/footballClub")
	public String showFootballClubList(final Map<String, Object> model) {

		//Creamos una colección de equipos
		FootballClubs footballClubs = new FootballClubs();

		//La llenamos con todos los equipos de la db
		footballClubs.getFootballClubList().addAll(this.footballClubService.findFootballClubs());

		//Ponemos en el modelo la colección de equipos
		model.put("footballClubs", footballClubs);

		//Mandamos a la vista de listado de equipos
		return "footballClubs/footballClubList";
	}

	//Crear Club - Get
	@GetMapping(value = "/myfootballClub/new")
	public String initCreationForm(final Map<String, Object> model) {
		FootballClub footballClub = new FootballClub();
		model.put("news", true);
		model.put("footballClub", footballClub);

		return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
	}

	//Crear Club - Post
	@PostMapping(value = "/myfootballClub/new")
	public String processCreationForm(@Valid final FootballClub footballClub, final BindingResult result, final Map<String, Object> model) throws DataAccessException, DuplicatedNameException {

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Obtenemos el presidente del usuario actual conectado
		President thisUser = this.footballClubService.findPresidentByUsername(currentPrincipalName);

		//Si hay errores seguimos en la vista de creación
		if (result.hasErrors()) {
			model.put("news", true);
			return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
		} else {
			try {
				//Añadimos como presidente el user conectado
				footballClub.setPresident(thisUser);
				//Ponemos el número de fans a cero
				footballClub.setFans(0);
				//Ponemos el status en Drafted
				footballClub.setStatus(false);
				//Guardamos el equipo en la db
				this.footballClubService.saveFootballClub(footballClub);

				//Si capturamos excepción de nombre duplicado seguimos en la vista de creación
			} catch (DuplicatedNameException ex) {
				//Mostramos el mensaje de error en el atributo name
				result.rejectValue("name", "duplicate", "already exists");
				return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
			}
			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/myfootballClub/" + currentPrincipalName;
		}
	}

	//Editar Club - Get
	@GetMapping(value = "/myfootballClub/{principalUsername}/edit")
	public String initUpdateFootballClubForm(@PathVariable("principalUsername") final String principalUsername, final Model model) {

		model.addAttribute("news", false);

		//Buscamos el equipo en la base de datos
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(principalUsername);
		//Añadimos al modelo los atributos del equipo a editar
		model.addAttribute(footballClub);

		//Obtenemos el username del usuario conectado actual
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Validación: Si el equipo está publicado no se puede editar ó
		//Si el usuario actual no coincide con el de la url lanzamos la pantalla de error
		if (!currentPrincipalName.equals(principalUsername) || footballClub.getStatus() == true) {
			return "redirect:/oups";
		}

		//Seguimos en la pantalla de edición
		return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
	}

	//Editar Club - Post
	@PostMapping(value = "/myfootballClub/{principalUsername}/edit")
	public String processUpdateFootballClubForm(@Valid final FootballClub footballClub, final BindingResult result, @PathVariable("principalUsername") final String principalUsername, final Model model) throws DataAccessException, DuplicatedNameException {

		//Si hay errores en la vista seguimos en la pantalla de edición
		if (result.hasErrors()) {
			model.addAttribute("news", false);
			return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
		} else {

			//Obtenemos el username del usuario actual conectado
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();

			//Buscamos en la db el equipo del user actual conectado
			FootballClub footballClubToUpdate = this.footballClubService.findFootballClubByPresident(principalUsername);

			//Copiamos los datos del equipo actual(vista del modelo) al equipo a actualizar excepto el presidente y la id(para que siga siendo el mismo)
			BeanUtils.copyProperties(footballClub, footballClubToUpdate, "id", "president");

			try {

				//Validación mínimo 5 jugadores
				Collection<FootballPlayer> cp = this.footballPlayerService.findAllClubFootballPlayers(footballClubToUpdate.getId());
				if (cp.size() < 5 && footballClubToUpdate.getStatus() == true) {
					model.addAttribute("publish", true);
					result.rejectValue("status", "min5players");
					return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
				}

				//Si todo va bien guardamos los cambios en la db
				this.footballClubService.saveFootballClub(footballClubToUpdate);

				//Si capturamos excepción de nombre duplicado seguimos en la vista de edición
			} catch (DuplicatedNameException ex) {
				//Mostramos el mensaje de error en el atributo name
				result.rejectValue("name", "duplicate", "already exists");
				return FootballClubController.VIEWS_CLUB_CREATE_OR_UPDATE_FORM;
			}

			//Si todo sale bien vamos a la vista de mi club
			return "redirect:/myfootballClub/" + currentPrincipalName;
		}
	}

	//Vista de Club por Id - Authenticateds
	@GetMapping("/footballClub/{footballClubId}")
	public ModelAndView showFootballClub(@PathVariable("footballClubId") final int footballClubId) {
		//Creamos la vista de equipo con la url del archivo.jsp de la vista
		ModelAndView mav = new ModelAndView("footballClubs/footballClubDetails");
		//Añadimos los datos del equipo según la id de la url
		mav.addObject(this.footballClubService.findFootballClubById(footballClubId));
		//devolvemos la vista con los datos del equipo en cuestión
		return mav;
	}

	//Vista de Club por Id - Presidentes
	@GetMapping("/myfootballClub/{principalUsername}")
	public ModelAndView showMyFootballClub(@PathVariable("principalUsername") final String principalUsername) {

		//Buscamos el equipo del presidente
		FootballClub footballClub = this.footballClubService.findFootballClubByPresident(principalUsername);

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Si no coincide el usuario actual conectado con el de la url mandamos a vista de error
		if (!currentPrincipalName.equals(principalUsername)) {
			ModelAndView mav = new ModelAndView("/exception");
			return mav;
		}

		//Si no hay club se manda a la vista para que cree un club
		if (footballClub == null) {
			ModelAndView mav = new ModelAndView("footballClubs/myClubEmpty");
			return mav;
		} else {

			//Si todo va bien mandamos a la vista normal de vista de club del presidente
			ModelAndView mav = new ModelAndView("footballClubs/myClubDetails");
			mav.addObject(this.footballClubService.findFootballClubByPresident(principalUsername));
			return mav;
		}
	}

	//Borrar Club
	@RequestMapping(value = "/myfootballClub/delete")
	public String processDeleteForm() {

		//Obtenemos el username del usuario actual conectado
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//Buscamos el equipo del presidente
		FootballClub thisFootballCLub = this.footballClubService.findFootballClubByPresident(currentPrincipalName);

		//Borramos el equipo en cuestión
		this.footballClubService.deleteFootballClub(thisFootballCLub);

		//Volvemos a la vista de mi club, en este caso sería la de "club empty"
		return "redirect:/myfootballClub/" + currentPrincipalName;
	}

}
