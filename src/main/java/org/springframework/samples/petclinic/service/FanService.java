
package org.springframework.samples.petclinic.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Fan;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.repository.FanRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedFanUserException;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
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
	public void saveFan(@Valid final Fan f) throws DataAccessException, DuplicatedFanUserException, DuplicatedNameException {
		Fan otherf = this.findByUserId(f.getUser().getId());
		FootballClub c = f.getClub();
		if (otherf != null) {
			if (this.existFan(f.getUser().getId()) && !f.getId().equals(otherf.getId())) {
				throw new DuplicatedFanUserException();
			} else {
				if (f.isVip()) {
					c.setMoney(c.getMoney() + 500);
				}
				this.fanRepository.save(f);
			}
		} else {
			c.setFans(c.getFans() + 1);
			if (f.isVip()) {
				c.setMoney(c.getMoney() + 500);
			}
			this.fanRepository.save(f);
		}

	}
	@Transactional()
	public boolean existFan(final int auId) throws DataAccessException {
		return this.fanRepository.existsByUserId(auId);
	}

	@Transactional()
	public Fan findByUserId(final Integer id) {

		return this.fanRepository.findByUserId(id);
	}

	@Transactional()
	public void delete(@Valid final Fan f) throws DataAccessException {

		FootballClub c = f.getClub();
		c.setFans(c.getFans() - 1);

		this.fanRepository.delete(f);

	}

}
