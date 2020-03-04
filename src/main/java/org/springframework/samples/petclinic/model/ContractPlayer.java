
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contract_player")
public class ContractPlayer extends Contract {

	@OneToOne(optional = false)
	@JoinColumn(name = "football_Players_id")
	private FootballPlayer	player;

	//Mínimo y Máximo
	@Column(name = "salary")
	private Integer			salary;


	//Getters and Setters

	public Integer getSalary() {
		return this.salary;
	}

	public void setSalary(final Integer salary) {
		this.salary = salary;
	}

	public FootballPlayer getPlayer() {
		return this.player;
	}

	public void setPlayer(final FootballPlayer player) {
		this.player = player;
	}
}
