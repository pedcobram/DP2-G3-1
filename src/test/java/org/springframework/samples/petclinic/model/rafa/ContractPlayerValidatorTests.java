
package org.springframework.samples.petclinic.model.rafa;

import java.util.Date;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.ContractPlayer;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.FootballPlayer;
import org.springframework.samples.petclinic.model.Enum.FootballPlayerPosition;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

class ContractPlayerValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateWhenClubNull() {

		//Creating a Player for the ContractPlayer
		FootballPlayer player = new FootballPlayer();
		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);
		Date date = new Date(System.currentTimeMillis() - 1);
		player.setBirthDate(date);

		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractPlayer contractPlayer = new ContractPlayer();

		contractPlayer.setClub(null);
		contractPlayer.setClause(1000000);
		contractPlayer.setSalary(1000000);
		contractPlayer.setStartDate(date);
		contractPlayer.setEndDate(date);
		contractPlayer.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractPlayer>> constraintViolations = validator.validate(contractPlayer);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<ContractPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("club");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenStartDateNull() {

		//Creating a Club for the ContractPlayer
		FootballClub club = new FootballClub();
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);
		Date date = new Date(System.currentTimeMillis() - 1);
		club.setFoundationDate(date);

		//Creating a Player for the ContractPlayer
		FootballPlayer player = new FootballPlayer();
		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);
		player.setBirthDate(date);

		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractPlayer contractPlayer = new ContractPlayer();

		contractPlayer.setClub(club);
		contractPlayer.setClause(1000000);
		contractPlayer.setSalary(1000000);
		contractPlayer.setStartDate(null);
		contractPlayer.setEndDate(date);
		contractPlayer.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractPlayer>> constraintViolations = validator.validate(contractPlayer);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<ContractPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("startDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenEndDateNull() {

		//Creating a Club for the ContractPlayer
		FootballClub club = new FootballClub();
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);
		Date date = new Date(System.currentTimeMillis() - 1);
		club.setFoundationDate(date);

		//Creating a Player for the ContractPlayer
		FootballPlayer player = new FootballPlayer();
		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);
		player.setBirthDate(date);

		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractPlayer contractPlayer = new ContractPlayer();

		contractPlayer.setClub(club);
		contractPlayer.setClause(1000000);
		contractPlayer.setSalary(1000000);
		contractPlayer.setStartDate(date);
		contractPlayer.setEndDate(null);
		contractPlayer.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractPlayer>> constraintViolations = validator.validate(contractPlayer);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<ContractPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("endDate");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenClauseNull() {

		//Creating a Club for the ContractPlayer
		FootballClub club = new FootballClub();
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);
		Date date = new Date(System.currentTimeMillis() - 1);
		club.setFoundationDate(date);

		//Creating a Player for the ContractPlayer
		FootballPlayer player = new FootballPlayer();
		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);
		player.setBirthDate(date);

		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractPlayer contractPlayer = new ContractPlayer();

		contractPlayer.setClub(club);
		contractPlayer.setClause(null);
		contractPlayer.setSalary(1000000);
		contractPlayer.setStartDate(date);
		contractPlayer.setEndDate(date);
		contractPlayer.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractPlayer>> constraintViolations = validator.validate(contractPlayer);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<ContractPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("clause");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenPlayerNull() {

		//Creating a Club for the ContractPlayer
		FootballClub club = new FootballClub();
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);
		Date date = new Date(System.currentTimeMillis() - 1);
		club.setFoundationDate(date);

		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractPlayer contractPlayer = new ContractPlayer();

		contractPlayer.setClub(club);
		contractPlayer.setClause(1000000);
		contractPlayer.setSalary(1000000);
		contractPlayer.setStartDate(date);
		contractPlayer.setEndDate(date);
		contractPlayer.setPlayer(null);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractPlayer>> constraintViolations = validator.validate(contractPlayer);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<ContractPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("player");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldNotValidateWhenSalaryNull() {

		//Creating a Club for the ContractPlayer
		FootballClub club = new FootballClub();
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);
		Date date = new Date(System.currentTimeMillis() - 1);
		club.setFoundationDate(date);

		//Creating a Player for the ContractPlayer
		FootballPlayer player = new FootballPlayer();
		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);
		player.setBirthDate(date);

		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractPlayer contractPlayer = new ContractPlayer();

		contractPlayer.setClub(club);
		contractPlayer.setClause(1000000);
		contractPlayer.setSalary(null);
		contractPlayer.setStartDate(date);
		contractPlayer.setEndDate(date);
		contractPlayer.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractPlayer>> constraintViolations = validator.validate(contractPlayer);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<ContractPlayer> violation = constraintViolations.iterator().next();
		Assertions.assertThat(violation.getPropertyPath().toString()).isEqualTo("salary");
		Assertions.assertThat(violation.getMessage()).isEqualTo("must not be null");
	}

	@Test
	void shouldValidateWell() {

		//Creating a Club for the ContractPlayer
		FootballClub club = new FootballClub();
		club.setName("Chelsea");
		club.setCity("London");
		club.setStadium("Stamford Bridge");
		club.setMoney(1000000);
		Date date = new Date(System.currentTimeMillis() - 1);
		club.setFoundationDate(date);

		//Creating a Player for the ContractPlayer
		FootballPlayer player = new FootballPlayer();
		player.setFirstName("Diego Armando");
		player.setLastName("Maradona");
		player.setValue(10000000);
		player.setPosition(FootballPlayerPosition.MIDFIELDER);
		player.setBirthDate(date);

		//Testing Contract
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		ContractPlayer contractPlayer = new ContractPlayer();

		contractPlayer.setClub(club);
		contractPlayer.setClause(1000000);
		contractPlayer.setSalary(1000000);
		contractPlayer.setStartDate(date);
		contractPlayer.setEndDate(date);
		contractPlayer.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<ContractPlayer>> constraintViolations = validator.validate(contractPlayer);

		Assertions.assertThat(constraintViolations.size()).isEqualTo(0);

	}

}
