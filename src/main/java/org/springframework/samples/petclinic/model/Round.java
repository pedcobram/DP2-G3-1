
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Round extends NamedEntity {

	//	@ElementCollection(targetClass = Match.class)
	//	private List<Match>	matches;

	@ManyToOne()
	@JoinColumn(name = "competition_id", referencedColumnName = "id")
	@Valid
	private Competition competition;


	public void nameRounds(final int nClubs) {
		String res = "";
		switch (nClubs) {
		case 2:
			res = "Final";
			break;
		case 4:
			res = "Semifinal";
			break;
		case 8:
			res = "Cuartos de final";
			break;
		case 16:
			res = "Octavos de final";
			break;
		case 32:
			res = "Dieciseisavos de Final";
			break;
		default:
			res = "Ronda";

		}
		this.setName(res);
	}
}
