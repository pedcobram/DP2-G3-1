
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "jornada")
public class Jornada extends BaseEntity {

	@ManyToOne(optional = false)
	@JoinColumn(name = "calendary_id")
	private Calendary	calendary;

	@Column(name = "name")
	@NotNull
	private String		name;
}
