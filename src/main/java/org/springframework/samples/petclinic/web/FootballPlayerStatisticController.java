
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.FootballPlayerStatistic;
import org.springframework.samples.petclinic.service.FootballPlayerStatisticService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FootballPlayerStatisticController {

	private static final String						VIEWS_FOOTBALL_PLAYER_STATISTIC_DETAIL	= "footballPlayerStatistic/footballPlayerStatisticDetails";

	private final FootballPlayerStatisticService	footballPlayerStatisticService;


	@Autowired
	public FootballPlayerStatisticController(final FootballPlayerStatisticService footballPlayerStatisticService) {
		this.footballPlayerStatisticService = footballPlayerStatisticService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(value = "/footballPlayerStatistic/detail/{playerId}")
	public String viewFootballPlayerStatistic(@PathVariable("playerId") final int playerId, final ModelMap model) {

		FootballPlayerStatistic fps = this.footballPlayerStatisticService.findFootballPlayerStatisticByPlayerId(playerId);

		model.addAttribute("fps", fps);

		return FootballPlayerStatisticController.VIEWS_FOOTBALL_PLAYER_STATISTIC_DETAIL;
	}

}
