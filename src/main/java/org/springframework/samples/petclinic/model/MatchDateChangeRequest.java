
package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.Enum.RequestStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "match_date_change_requests")
public class MatchDateChangeRequest extends BaseEntity {

	@Column(name = "title")
	@NotNull
	private String			title;

	@Column(name = "status")
	private RequestStatus	status;

	@Column(name = "new_date")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	private Date			new_date;

	@Column(name = "reason")
	@NotNull
	private String			reason;

	@Column(name = "request_creator")
	@NotNull
	private String			request_creator;

	//

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "match_id", referencedColumnName = "id")
	private Match			match;

}
