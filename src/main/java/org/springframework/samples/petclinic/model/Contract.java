
package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contract")
public class Contract extends BaseEntity {

	/**
	 * HACER CLASE PADRE MEJOR.
	 * UN CONTRATO TIENE UNA DURACIÓN, UN CLUB, UN JUGADOR O INVERSIÓN(sin implementar de momento)
	 * Y UN MONEY QUE SERÁ SUELDO EN CASO DE JUGADOR O FONDOS AÑADIDOS EN CASO DE INVERSIÓN
	 **/

	//Atributos

	@ManyToOne(optional = true)
	@JoinColumn(name = "football_Clubs_id")
	private FootballClub	club;

	@OneToOne(optional = true)
	@JoinColumn(name = "football_Players_id")
	private FootballPlayer	player;

	//	@OneToOne(optional = true)
	//	@JoinColumn(name = "investment_id")
	//	private Investment	investment;

	@Column(name = "duration")
	private Date			duration;

	@Column(name = "money")
	private Integer			money;


	//Getters and Setters

	public FootballClub getClub() {
		return this.club;
	}

	public void setClub(final FootballClub club) {
		this.club = club;
	}

	public FootballPlayer getPlayer() {
		return this.player;
	}

	public void setPlayer(final FootballPlayer player) {
		this.player = player;
	}

	public Date getDuration() {
		return this.duration;
	}

	public void setDuration(final Date duration) {
		this.duration = duration;
	}

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(final Integer money) {
		this.money = money;
	}
}
