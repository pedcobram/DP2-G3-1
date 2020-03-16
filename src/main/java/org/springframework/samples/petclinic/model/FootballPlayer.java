
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.core.style.ToStringCreator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "football_Players")
public class FootballPlayer extends Person {

	//Atributos

	@ManyToOne(optional = true)
	@JoinColumn(name = "football_Clubs_id")
	private FootballClub			club;

	@Column(name = "value")
	@NotNull
	private Integer					value;

	@Column(name = "position")
	@NotNull
	private FootballPlayerPosition	position;

	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@NotNull
	private Date					birthDate;


	public Integer getAge() {

		Date safeDate = new Date(this.getBirthDate().getTime()); //Si no hacemos esto nos da error UnsuportedOperationException
		LocalDate birth = safeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate now = LocalDate.now();
		Period diff = Period.between(birth, now);

		return diff.getYears();
	}

	//ToString

	@Override
	public String toString() {
		return new ToStringCreator(this)

			.append("id", this.getId()).append("new", this.isNew()).append("lastName", this.getLastName()).append("firstName", this.getFirstName()).append("birthDate", this.birthDate).append("position", this.position).append("club", this.club).toString();
	}
}
