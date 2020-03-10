
package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.repository.CompetitionAdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetitionAdminService {

	private CompetitionAdminRepository	competitionAdminRepository;

	@Autowired
	private AuthoritiesService			authoritiesService;


	@Autowired
	public CompetitionAdminService(final CompetitionAdminRepository competitionAdminRepository) {
		this.competitionAdminRepository = competitionAdminRepository;
	}

	@Transactional(readOnly = true)
	public CompetitionAdmin findCompetitionAdminById(final int id) throws DataAccessException {
		return this.competitionAdminRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public CompetitionAdmin findCompetitionAdminByUsername(final String userName) throws DataAccessException {
		return this.competitionAdminRepository.findByUsername(userName);
	}

	@Transactional(readOnly = true)
	public Authenticated findAuthenticatedByUsername(final String userName) throws DataAccessException {
		return this.competitionAdminRepository.findAuthenticatedByUsername(userName);
	}

	@Transactional(readOnly = true)
	public int count() throws DataAccessException {
		return this.competitionAdminRepository.count();
	}

	@Transactional
	public void saveCompetitionAdmin(final CompetitionAdmin competitionAdmin) throws DataAccessException {
		this.competitionAdminRepository.save(competitionAdmin);
		this.authoritiesService.saveAuthorities(competitionAdmin.getUser().getUsername(), "competitionAdmin");
	}

	@Transactional()
	public void deleteCompetitionAdmin(final CompetitionAdmin competitionAdmin) throws DataAccessException {
		this.authoritiesService.deleteAuthorities(competitionAdmin.getUser().getUsername(), "competitionAdmin");
		this.authoritiesService.saveAuthorities(competitionAdmin.getUser().getUsername(), "authenticated");
		this.competitionAdminRepository.delete(competitionAdmin);
	}

}
