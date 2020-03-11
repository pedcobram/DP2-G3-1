
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.repository.FanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FanService {

	private FanRepository fanRepository;


	@Autowired
	public FanService(final FanRepository fanRepository) {
		this.fanRepository = fanRepository;

	}

	//Buscar todos los contratos de jugadores
	@Transactional(readOnly = true)
	public void saveFan(final Fan f) throws DataAccessException {
		this.fanRepository.save(f);
	}

}
