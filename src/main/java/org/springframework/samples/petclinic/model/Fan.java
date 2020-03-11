
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
public class Fan extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "club_id", referencedColumnName = "id")
	@Valid
	private FootballClub	club;

	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Authenticated	user;

	@NotNull
	private boolean			status;

}
