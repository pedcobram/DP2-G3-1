
package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@MappedSuperclass
public class Contract extends BaseEntity {

	//Atributos

	@ManyToOne(optional = true)
	@JoinColumn(name = "football_Clubs_id")
	@NotNull
	private FootballClub	club;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "start_date")
	@NotNull
	private Date			startDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "end_date")
	@NotNull
	private Date			endDate;

	@Column(name = "end_contract_clause")
	@NotNull
	private Integer			clause;
}
