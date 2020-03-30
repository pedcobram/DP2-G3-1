
package org.springframework.samples.petclinic.model.Enum;

import javax.persistence.Table;

@Table(name = "position")
public enum FootballPlayerPosition {
	GOALKEEPER, DEFENDER, MIDFIELDER, STRIKER
}
