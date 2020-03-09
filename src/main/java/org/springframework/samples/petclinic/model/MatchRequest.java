
package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "match_requests")
public class MatchRequest extends BaseEntity {

	@Column(name = "title")
	@NotEmpty
	private String			title;

	@Column(name = "match_date")
	@NotEmpty
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private Date			matchDate;

	@Column(name = "stadium")
	@NotEmpty
	private String			stadium;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "footballClub1", referencedColumnName = "name")
	private FootballClub	footballClub1;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "footballClub2", referencedColumnName = "name")
	private FootballClub	footballClub2;

	// Referees TBD
	//private String referee;

}
