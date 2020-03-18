
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "Match_Record")
public class MatchRecord extends BaseEntity {

	@Column(name = "title")
	@NotBlank
	private String				title;

	@Column(name = "status")
	@NotNull
	private MatchRecordStatus	status;

	@Column(name = "season_start")
	@Pattern(regexp = "^\\d{4}$|^$", message = "Must be a four digit number")
	private String				season_start;

	@Column(name = "season_end")
	@Pattern(regexp = "^\\d{4}$|^$", message = "Must be a four digit number")
	private String				season_end;

	@Column(name = "result")
	private String				result;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "match", referencedColumnName = "id")
	private Match				match;

}
