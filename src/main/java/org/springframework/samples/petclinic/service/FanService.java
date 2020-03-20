
package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.repository.FanRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedFanUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FanService {

	private FanRepository fanRepository;


	@Autowired
	public FanService(final FanRepository fanRepository) {
		this.fanRepository = fanRepository;

	}

	@Transactional(rollbackFor = DuplicatedFanUserException.class)
	public void saveFan(@Valid final Fan f) throws DataAccessException, DuplicatedFanUserException {
		Fan otherf = this.findByUserId(f.getUser().getId());
		if (otherf != null) {
			if (this.existFan(f.getUser().getId()) && f.getId() != otherf.getId()) {
				throw new DuplicatedFanUserException();
			} else {
				this.fanRepository.save(f);
			}
		} else {
			this.fanRepository.save(f);
		}

	}
	@Transactional()
	public boolean existFan(final int auId) throws DataAccessException {
		return this.fanRepository.existsByUserId(auId);
	}

	public Fan findByUserId(final Integer id) {

		return this.fanRepository.findByUserId(id);
	}

	public void delete(final Fan f) {
		this.fanRepository.delete(f);

	}

}
