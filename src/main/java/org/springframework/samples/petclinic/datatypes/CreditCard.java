
package org.springframework.samples.petclinic.datatypes;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.samples.petclinic.datatypes.enume.CreditCardBrand;

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

	@NotNull
	@Enumerated
	private CreditCardBrand		marca;

	@NotBlank
	@Pattern(regexp = "^(1[0-2]|0[1-9]|\\d)\\/(\\d{2})$", message = "“MM/YY”")
	private String				expirationDate;

	@NotBlank
	@Pattern(regexp = "^\\d{3}$", message = "“999”")
	private String				cvv;

}
