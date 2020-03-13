
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Referee;
import org.springframework.samples.petclinic.repository.RefereeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RefereeService {

	private RefereeRepository	refereeRepository;

	@Autowired
	private AuthoritiesService	authoritiesService;


	@Autowired
	public RefereeService(final RefereeRepository refereeRepository) {
		this.refereeRepository = refereeRepository;
	}

	@Transactional(readOnly = true)
	public Referee findRefereeById(final int id) throws DataAccessException {
		return this.refereeRepository.findRefereeById(id);
	}

	@Transactional(readOnly = true)
	public Referee findRefereeByUsername(final String userName) throws DataAccessException {
		return this.refereeRepository.findRefereeByUsername(userName);
	}

	@Transactional(readOnly = true)
	public Collection<Referee> findAllReferees() throws DataAccessException {
		return this.refereeRepository.findAllReferees();
	}

	@Transactional()
	public void saveReferee(final Referee referee) throws DataAccessException {
		this.refereeRepository.save(referee);
		this.authoritiesService.saveAuthorities(referee.getUser().getUsername(), "referee");
	}

	@Transactional()
	public void deleteReferee(final Referee referee) throws DataAccessException {
		this.authoritiesService.deleteAuthorities(referee.getUser().getUsername(), "referee");
		this.authoritiesService.saveAuthorities(referee.getUser().getUsername(), "authenticated");
		this.refereeRepository.delete(referee);

	}

	@Transactional()
	public int count() {
		return this.refereeRepository.count();
	}
}
