
package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "contract_commercial")
public class ContractCommercial extends Contract {

	@Column(name = "money")			//Fondos a añadir al presupuesto del club
	private Integer	money;

	@Column(name = "publicity")     //URL con la imagen de la publicidad
	@URL
	private String	publicity;


	//Getters and Setters

	public Integer getMoney() {
		return this.money;
	}

	public void setMoney(final Integer money) {
		this.money = money;
	}

	public String getPublicity() {
		return this.publicity;
	}

	public void setPublicity(final String publicity) {
		this.publicity = publicity;
	}
}
