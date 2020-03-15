
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "contract_player")
public class ContractPlayer extends Contract {

	@OneToOne(optional = false)
	@JoinColumn(name = "football_Players_id")
	@NotNull
	private FootballPlayer	player;

	//Mínimo y Máximo
	@Column(name = "salary")
	@NotNull
	private Integer			salary;

}
