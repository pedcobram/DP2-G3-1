
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Fan extends BaseEntity {

	@NotNull
	private FootballClub	club;

	@NotNull
	private Authenticated	user;

	@NotNull
	private boolean			status;

}
