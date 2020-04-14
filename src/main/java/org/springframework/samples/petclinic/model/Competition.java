
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.Enum.CompetitionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "competitions")
public class Competition extends BaseEntity {

	//Atributos

	@Column(name = "name")
	@NotNull
	private String			name;

	@Column(name = "description")
	@NotNull
	private String			description;

	@Column(name = "type")
	@NotNull
	private CompetitionType	type;

	@Column(name = "reward")
	@NotNull
	private Integer			reward;

	@Column(name = "status")
	private Boolean			status;

	//Calendar calendario;

	//Set<Club> clubs;

}
