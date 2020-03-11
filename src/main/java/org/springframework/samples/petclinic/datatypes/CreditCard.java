
package org.springframework.samples.petclinic.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CreditCard extends DomainDatatype {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@CreditCardNumber
	private String				creditCardNumber;

	@NotBlank
	@Pattern(regexp = "^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$", message = "“MM/YY”")
	private String				expirationDate;

	@NotBlank
	@Pattern(regexp = "^\\d{3}$", message = "“999”")
	private String				cvv;

}
