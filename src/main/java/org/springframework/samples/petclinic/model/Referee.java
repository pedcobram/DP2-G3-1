
package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "referees")
public class Referee extends Person implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String				telephone;

	@Column(name = "email")
	@NotEmpty
	private String				email;

	@Column(name = "dni")
	@NotEmpty
	private String				dni;

	//
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User				user;
	//

}
