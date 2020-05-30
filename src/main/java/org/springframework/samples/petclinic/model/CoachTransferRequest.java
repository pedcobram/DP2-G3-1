
package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.samples.petclinic.model.Enum.RequestStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "coach_transfer_request")
public class CoachTransferRequest extends BaseEntity implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	@Column(name = "offer")
	@NotNull
	@Min(value = 1000000)
	@Max(value = 25000000)
	private Long				offer;

	@Column(name = "status")
	private RequestStatus		status;

	//

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "requestedCoach", referencedColumnName = "id")
	private Coach				requestedCoach;

	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "myCoach", referencedColumnName = "id")
	private Coach				myCoach;

	//

}
