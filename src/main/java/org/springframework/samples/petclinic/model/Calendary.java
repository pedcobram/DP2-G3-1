
package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "calendary")
public class Calendary extends BaseEntity {

	@OneToOne(optional = true)
	@JoinColumn(name = "competition_id")
	private Competition competition;
}
