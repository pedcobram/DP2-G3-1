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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "football_Clubs")
public class FootballClub extends BaseEntity implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@OneToOne(optional = false)
	@JoinColumn(name = "president_id")
	@NotNull
	private President			president;

	@Column(name = "crest")
	@URL
	private String				crest;

	@Column(name = "name")
	@NotEmpty
	private String				name;

	@Column(name = "foundation_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private Date				foundationDate;

	@Column(name = "stadium")
	@NotEmpty
	private String				stadium;

	@Column(name = "city")
	@NotEmpty
	private String				city;

	@Column(name = "money")
	@NotNull
	private Integer				money;

	@Column(name = "fans")
	@NotNull
	private Integer				fans;

	@Column(name = "status")
	@NotNull
	private Boolean				status;
}
