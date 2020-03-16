
package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.FootballPlayerMatchStatisticService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class FootballPlayerMatchStatisticController {

	//private static final String						VIEWS_FOOTBALL_PLAYER_MATCH_STATISTIC_CREATE_OR_UPDATE_FORM	= "footballPlayerMatchStatistic/createOrUpdateFootballPlayerMatchStatisticForm";

	private static final String							VIEWS_FOOTBALL_PLAYER_STATISTIC_DETAIL	= "footballPlayerMatchStatistic/footballPlayerMatchStatisticDetails";

	private final FootballPlayerMatchStatisticService	footballPlayerMatchStatisticService;


	@Autowired
	public FootballPlayerMatchStatisticController(final FootballPlayerMatchStatisticService footballPlayerMatchStatisticService) {
		this.footballPlayerMatchStatisticService = footballPlayerMatchStatisticService;
	}

	@InitBinder
	public void setAllowedFields(final WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

}
