
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contract_commercial")
public class ContractCommercial extends Contract {

	@Column(name = "money")		//Fondos a añadir al presupuesto del club
	@NotNull
	private Integer	money;

	@Column(name = "publicity")     //URL con la imagen de la publicidad
	@URL
	@NotNull
	private String	publicity;

}
