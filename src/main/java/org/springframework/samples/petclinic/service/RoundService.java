
package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Round;
import org.springframework.samples.petclinic.repository.RoundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoundService {

	private RoundRepository roundRepository;


	@Autowired
	public RoundService(final RoundRepository roundRepository) {
		this.roundRepository = roundRepository;

	}

	@Transactional()
	public void delete(@Valid final Round r) throws DataAccessException {

		this.roundRepository.deleteById(r.getId());

	}

	public void save(final Round r1) {
		this.roundRepository.save(r1);

	}

}
