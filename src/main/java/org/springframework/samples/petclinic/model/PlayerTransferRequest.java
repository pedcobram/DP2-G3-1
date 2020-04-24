
package org.springframework.samples.petclinic.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.Enum.RequestStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "player_transfer_request")
public class PlayerTransferRequest extends BaseEntity implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	@Column(name = "playerValue")
	private Integer				playerValue;

	@Column(name = "status")
	private RequestStatus		status;

	//

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "playerId", referencedColumnName = "id")
	private FootballPlayer		footballPlayer;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "president", referencedColumnName = "username")
	private President			president;
	//

}
