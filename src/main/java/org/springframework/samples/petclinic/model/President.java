/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import javax.validation.constraints.Pattern;

import org.springframework.core.style.ToStringCreator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "presidents")
public class President extends Person implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	@Column(name = "telephone")
	@Digits(fraction = 0, integer = 10)
	@NotEmpty
	private String				telephone;

	@Column(name = "email")
	@NotEmpty
	private String				email;

	@Column(name = "dni")
	@NotEmpty
	@Pattern(regexp = "[0-9]{7,8}[A-Za-z]")
	private String				dni;

	//
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User				user;
	//


	@Override
	public String toString() {
		return new ToStringCreator(this)

			.append("id", this.getId()).append("new", this.isNew()).append("lastName", this.getLastName()).append("firstName", this.getFirstName()).append("dni", this.dni).append("email", this.email).append("telephone", this.telephone).toString();
	}

}
