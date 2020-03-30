
package org.springframework.samples.petclinic.datatypes;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
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

	@NotEmpty
	@CreditCardNumber
	private String				creditCardNumber;

	@NotEmpty
	@Pattern(regexp = "^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$", message = "“MM/YY”")
	private String				expirationDate;

	@NotEmpty
	@Pattern(regexp = "^\\d{3}$", message = "“999”")
	private String				cvv;

}
