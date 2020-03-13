
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "coachs")
public class Coach extends Person {

	//Atributos

	@OneToOne(optional = true)
	@JoinColumn(name = "football_Clubs_id")
	private FootballClub	club;

	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private Date			birthDate;

	@Column(name = "salary")
	@NotNull
	private Integer			salary;

	@Column(name = "clause")
	private Integer			clause;


	public Integer getAge() {

		Date safeDate = new Date(this.getBirthDate().getTime()); //Si no hacemos esto nos da error UnsuportedOperationException
		LocalDate birth = safeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate now = LocalDate.now();
		Period diff = Period.between(birth, now);

		return diff.getYears();
	}
}
