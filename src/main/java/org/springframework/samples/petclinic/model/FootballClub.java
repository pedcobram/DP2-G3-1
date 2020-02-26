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

package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "football_Clubs")
public class FootballClub extends BaseEntity {

	@Column(name = "foundation_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private Date		foundationDate;

	@OneToOne(optional = false)
	@JoinColumn(name = "president_id")
	private President	president;

	@Column(name = "crest")
	@URL
	private String		crest;

	@Column(name = "name")
	@NotEmpty
	private String		name;

	@Column(name = "stadium")
	@NotEmpty
	private String		stadium;

	@Column(name = "city")
	@NotEmpty
	private String		city;

	@Column(name = "fans")
	private Integer		fans;

	@Column(name = "coach")
	private String		coach;

	@Column(name = "money")
	@NotNull
	private Integer		money;

	//	@OneToMany(cascade = CascadeType.ALL)
	//	private Set<FootballPlayer>	players;

	//	private FootballCalendar		calendar;


	//GETTER AND SETTER

	public President getPresident() {
		return this.president;
	}

	public void setPresident(final President president) {
		this.president = president;
	}

	public String getCrest() {
		return this.crest;
	}

	public void setCrest(final String Crest) {
		this.crest = Crest;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String Name) {
		this.name = Name;
	}

	public void setFoundationDate(final Date foundationDate) {
		this.foundationDate = foundationDate;
	}

	public Date getFoundationDate() {
		return this.foundationDate;
	}

	public String getStadium() {
		return this.stadium;
	}

	public void setStadium(final String Stadium) {
		this.stadium = Stadium;
	}

	public Integer getFans() {
		return this.fans;
	}

	public void setFans(final Integer Fans) {
		this.fans = Fans;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String City) {
		this.city = City;
	}

	public String getCoach() {
		return this.coach;
	}

	public void setCoach(final String Coach) {
		this.coach = Coach;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(final Integer Money) {
		this.money = Money;
	}

	//FootballPlayers Getter and Setter
	/**
	 * protected Set<FootballPlayer> getFootballPlayersInternal() {
	 * if (this.players == null) {
	 * this.players = new HashSet<>();
	 * }
	 * return this.players;
	 * }
	 *
	 * protected void setFootballPlayersInternal(final Set<FootballPlayer> players) {
	 * this.players = players;
	 * }
	 *
	 * public List<FootballPlayer> getFootballPlayers() {
	 * List<FootballPlayer> sortedFootballPlayers = new ArrayList<>(this.getFootballPlayersInternal());
	 * PropertyComparator.sort(sortedFootballPlayers, new MutableSortDefinition("name", true, true));
	 * return Collections.unmodifiableList(sortedFootballPlayers);
	 * }
	 *
	 * public void addFootballPlayer(final FootballPlayer player) {
	 * this.getFootballPlayersInternal().add(player);
	 * player.setFootballClub(this);
	 * }
	 *
	 * public boolean removeFootballPlayer(final FootballPlayer player) {
	 * return this.getFootballPlayersInternal().remove(player);
	 * }
	 **/
}
