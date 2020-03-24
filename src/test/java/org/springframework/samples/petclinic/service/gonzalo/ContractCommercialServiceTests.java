
package org.springframework.samples.petclinic.service.gonzalo;

import java.util.Collection;

import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.ContractCommercial;
import org.springframework.samples.petclinic.service.ContractService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ContractCommercialServiceTests {

	@Autowired
	protected ContractService contractService;


	@Test
	void shouldFindAllCommercialContracts() {
		Collection<ContractCommercial> contracts = this.contractService.findAllCommercialContracts();
		Assertions.assertThat(contracts.size()).isEqualTo(2);
	}

	@Test
	void shouldFindAllCommercialContractsByClub() {
		Collection<ContractCommercial> contracts = this.contractService.findAllCommercialContractsByClubId(1);
		Assertions.assertThat(contracts.size()).isEqualTo(0);
	}

	@Test
	void shouldFindContractCommercialById() {
		ContractCommercial c1 = this.contractService.findContractCommercialById(1);
		Assertions.assertThat(c1.getClause()).isEqualTo(1000);
		Assertions.assertThat(c1.getMoney()).isEqualTo(100000);
		Assertions.assertThat(c1.getEndDate()).isEqualTo("2023-01-01");
		Assertions.assertThat(c1.getStartDate()).isEqualTo("2013-01-01");
	}

	@Test
	@Transactional
	void shouldSaveContractCommercial() {
		Collection<ContractCommercial> contracts = this.contractService.findAllCommercialContracts();
		int cantidadIni = contracts.size();

		//Empty new contract
		ContractCommercial c1 = new ContractCommercial();
		c1.setId(3);
		this.contractService.saveContractCommercial(c1);

		contracts = this.contractService.findAllCommercialContracts();
		Assertions.assertThat(contracts.size()).isEqualTo(cantidadIni + 1);
	}

	@Test
	@Transactional
	void shouldDeleteContractCommercial() {
		// PARA QUE ESTE TEST FUNCIONE PRIMERO SE DEBE ASEGURAR QUE EL METODO "SAVE"
		// DE CONTRACTSERVICE NO TIENE FALLOS
		Collection<ContractCommercial> contracts = this.contractService.findAllCommercialContracts();
		int cantidadIni = contracts.size();

		//Añadimos nuevo contracto
		ContractCommercial c1 = new ContractCommercial();
		c1.setId(3);
		this.contractService.saveContractCommercial(c1);

		//Eliminamos c1
		this.contractService.deleteContract(c1);

		contracts = this.contractService.findAllCommercialContracts();
		Assertions.assertThat(contracts.size()).isEqualTo(cantidadIni);
	}

}
