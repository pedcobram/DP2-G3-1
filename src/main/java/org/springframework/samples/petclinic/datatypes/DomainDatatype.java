/*
 * DomainDatatype.java
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

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public abstract class DomainDatatype implements Serializable {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;
}
