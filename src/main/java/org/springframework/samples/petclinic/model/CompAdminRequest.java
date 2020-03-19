
package org.springframework.samples.petclinic.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.Enum.RequestStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "comp_Admin_Requests")
public class CompAdminRequest extends BaseEntity {

	@NotEmpty
	@Column(name = "title")
	private String			title;

	@NotEmpty
	@Column(name = "description")
	private String			description;

	@Column(name = "status")
	private RequestStatus	status;

	//
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	private User			user;
	//
}
