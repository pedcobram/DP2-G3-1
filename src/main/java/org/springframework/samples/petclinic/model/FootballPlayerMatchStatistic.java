
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "football_player_match_statistic")
public class FootballPlayerMatchStatistic extends BaseEntity {

	@Column(name = "assists")
	@NotNull
	private Integer			assists;

	@Column(name = "goals")
	@NotNull
	private Integer			goals;

	@Column(name = "red_cards")
	@NotNull
	private Integer			red_cards;

	@Column(name = "yellow_cards")
	@NotNull
	private Integer			yellow_cards;

	//Si es portero

	@Column(name = "received_goals")
	private Integer			received_goals;

	//

	@Column(name = "season_start")
	@Pattern(regexp = "^\\d{4}$|^$")
	private String			season_start;

	@Column(name = "season_end")
	@Pattern(regexp = "^\\d{4}$|^$")
	private String			season_end;

	@ManyToOne(optional = true)
	@JoinColumn(name = "football_player_id", referencedColumnName = "id")
	private FootballPlayer	player;

	@ManyToOne(optional = true)
	@JoinColumn(name = "match_record", referencedColumnName = "id")
	private MatchRecord		matchRecord;

}
