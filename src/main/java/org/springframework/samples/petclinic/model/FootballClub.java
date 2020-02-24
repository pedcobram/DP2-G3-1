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

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "football_Clubs")
public class FootballClub extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "president_id")
	private President	president;

	@Column(name = "crest")
	private String		crest;

	@Column(name = "name")
	private String		name;

	@Column(name = "foundation_date")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate	foundationDate;

	@Column(name = "stadium")
	private String		stadium;

	@Column(name = "city")
	private String		city;

	@Column(name = "fans")
	private Integer		fans;

	//	private FootballCalendar		calendar;

	//	private List<FootballPlayer>	footballPlayers;

	@Column(name = "coach")
	private String		coach;

	//	@Column(name = "director")
	//	@OneToOne(optional = true)
	//	private Director	director;

	//	@Column(name = "inversor")
	//	@ManyToOne
	//	private Inversor	inversor;

	@Column(name = "money")
	private Integer		money;

	//	private List<Merchandise>		merchandise;


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

	public void setFoundationDate(final LocalDate foundationDate) {
		this.foundationDate = foundationDate;
	}

	public LocalDate getFoundationDate() {
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

}
