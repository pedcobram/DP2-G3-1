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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "inversors")
public class Inversor extends Person {

	@Column(name = "address")
	@NotEmpty
	private String	address;

	@Column(name = "city")
	@NotEmpty
	private String	city;

	@Column(name = "telephone")
	@NotEmpty
	@Digits(fraction = 0, integer = 10)
	private String	telephone;

	//	@OneToMany(cascade = CascadeType.ALL)
	//	private List<FootballClub>	club;

	@Column(name = "business")
	@NotEmpty
	private String	business;

	//
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User	user;
	//


	public String getAddress() {
		return this.address;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)

			.append("id", this.getId()).append("new", this.isNew()).append("lastName", this.getLastName()).append("firstName", this.getFirstName()).append("address", this.address).append("city", this.city).append("telephone", this.telephone).toString();
	}

}
