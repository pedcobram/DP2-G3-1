
package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	private FootballClub	club;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "start_date")
	private Date			startDate;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "end_date")
	private Date			endDate;

	//Automático
	@Column(name = "end_contract_clause")
	private Integer			clause;


	//Getters and Setters

	public FootballClub getClub() {
		return this.club;
	}

	public void setClub(final FootballClub club) {
		this.club = club;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public Integer getClause() {
		return this.clause;
	}

	public void setClause(final Integer clause) {
		this.clause = clause;
	}
}
