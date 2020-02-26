/*
 * Money.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package org.springframework.samples.petclinic.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Money extends DomainDatatype {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Digits(integer = 10, fraction = 2)
	@Min(0)
	private Double				amount;

	@NotBlank
	private String				currency;


	// Object interface -------------------------------------------------------

	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append("<<");
		result.append(this.currency);
		result.append(" ");
		result.append(String.format("%.2f", this.amount));
		result.append(">>");

		return result.toString();
	}

}
