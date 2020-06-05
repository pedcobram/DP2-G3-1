
package org.springframework.samples.petclinic.footballPlayerStatistic;

import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.model.FootballPlayerStatistic;
import org.springframework.samples.petclinic.model.MatchRecord;
import org.springframework.samples.petclinic.model.Enum.MatchRecordStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class FootballPlayerStatisticValidatorTests {

	private Validator createValidator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.afterPropertiesSet();
		return localValidatorFactoryBean;
	}

	@Test
	void shouldNotValidateAssists() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord matchRecord = new MatchRecord();

		matchRecord.setTitle("title");
		matchRecord.setResult("result");
		matchRecord.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		matchRecord.setSeason_end("2000");
		matchRecord.setSeason_start("2001");

		FootballPlayerStatistic fpms = new FootballPlayerStatistic();

		fpms.setAssists(null);
		fpms.setGoals(0);
		fpms.setRed_cards(0);
		fpms.setYellow_cards(0);
		fpms.setReceived_goals(0);
		fpms.setSeason_end(matchRecord.getSeason_end());
		fpms.setSeason_start(matchRecord.getSeason_start());
		//fpms.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayerStatistic>> constraintViolations = validator.validate(fpms);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<FootballPlayerStatistic> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("assists") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldNotValidateGoals() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord matchRecord = new MatchRecord();

		matchRecord.setTitle("title");
		matchRecord.setResult("result");
		matchRecord.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		matchRecord.setSeason_end("2000");
		matchRecord.setSeason_start("2001");
		//matchRecord.setMatch(match);

		FootballPlayerStatistic fpms = new FootballPlayerStatistic();

		fpms.setAssists(0);
		fpms.setGoals(null);
		fpms.setRed_cards(0);
		fpms.setYellow_cards(0);
		fpms.setReceived_goals(0);
		fpms.setSeason_end(matchRecord.getSeason_end());
		fpms.setSeason_start(matchRecord.getSeason_start());
		//fpms.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayerStatistic>> constraintViolations = validator.validate(fpms);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<FootballPlayerStatistic> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("goals") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldNotValidateRedCards() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord matchRecord = new MatchRecord();

		matchRecord.setTitle("title");
		matchRecord.setResult("result");
		matchRecord.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		matchRecord.setSeason_end("2000");
		matchRecord.setSeason_start("2001");
		//matchRecord.setMatch(match);

		FootballPlayerStatistic fpms = new FootballPlayerStatistic();

		fpms.setAssists(0);
		fpms.setGoals(0);
		fpms.setRed_cards(null);
		fpms.setYellow_cards(0);
		fpms.setReceived_goals(0);
		fpms.setSeason_end(matchRecord.getSeason_end());
		fpms.setSeason_start(matchRecord.getSeason_start());
		//fpms.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayerStatistic>> constraintViolations = validator.validate(fpms);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<FootballPlayerStatistic> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("red_cards") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldNotValidateYellowCards() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord matchRecord = new MatchRecord();

		matchRecord.setTitle("title");
		matchRecord.setResult("result");
		matchRecord.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		matchRecord.setSeason_end("2000");
		matchRecord.setSeason_start("2001");
		//matchRecord.setMatch(match);

		FootballPlayerStatistic fpms = new FootballPlayerStatistic();

		fpms.setAssists(0);
		fpms.setGoals(0);
		fpms.setRed_cards(0);
		fpms.setYellow_cards(null);
		fpms.setReceived_goals(0);
		fpms.setSeason_end(matchRecord.getSeason_end());
		fpms.setSeason_start(matchRecord.getSeason_start());
		//fpms.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayerStatistic>> constraintViolations = validator.validate(fpms);

		Assertions.assertTrue(constraintViolations.size() == 1);

		ConstraintViolation<FootballPlayerStatistic> violation = constraintViolations.iterator().next();

		Assertions.assertTrue(violation.getPropertyPath().toString().compareTo("yellow_cards") == 0);
		Assertions.assertTrue(violation.getMessage().compareTo("must not be null") == 0);

	}

	@Test
	void shouldValidateAll() {

		LocaleContextHolder.setLocale(Locale.ENGLISH);

		MatchRecord matchRecord = new MatchRecord();

		matchRecord.setTitle("title");
		matchRecord.setResult("result");
		matchRecord.setStatus(MatchRecordStatus.NOT_PUBLISHED);
		matchRecord.setSeason_end("2000");
		matchRecord.setSeason_start("2001");
		//matchRecord.setMatch(match);

		FootballPlayerStatistic fpms = new FootballPlayerStatistic();

		fpms.setAssists(0);
		fpms.setGoals(0);
		fpms.setRed_cards(0);
		fpms.setYellow_cards(0);
		fpms.setReceived_goals(null);
		fpms.setSeason_end(matchRecord.getSeason_end());
		fpms.setSeason_start(matchRecord.getSeason_start());
		//fpms.setPlayer(player);

		Validator validator = this.createValidator();
		Set<ConstraintViolation<FootballPlayerStatistic>> constraintViolations = validator.validate(fpms);

		Assertions.assertTrue(constraintViolations.size() == 0);

	}

}
