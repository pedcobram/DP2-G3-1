
package org.springframework.samples.petclinic.model.Enum;

import javax.persistence.Table;

@Table(name = "competition_type")
public enum CompetitionType {
	LEAGUE, PLAYOFFS
}
