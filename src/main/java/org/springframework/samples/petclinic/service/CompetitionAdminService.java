
package org.springframework.samples.petclinic.service;

import javax.security.auth.login.CredentialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Authenticated;
import org.springframework.samples.petclinic.model.CompetitionAdmin;
import org.springframework.samples.petclinic.repository.CompetitionAdminRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetitionAdminService {

	private CompetitionAdminRepository	competitionAdminRepository;

	@Autowired
	private AuthoritiesService			authoritiesService;

	@Autowired
	private AuthenticatedService		authenticatedService;


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
	public void saveCompetitionAdmin(final CompetitionAdmin competitionAdmin) throws DataAccessException, CredentialException {

		competitionAdmin.getUser().setEnabled(true);

		this.competitionAdminRepository.save(competitionAdmin);
		this.authoritiesService.saveAuthorities(competitionAdmin.getUser().getUsername(), "competitionAdmin");
	}

	@Transactional()
	public void deleteCompetitionAdmin(final CompetitionAdmin competitionAdmin) throws DataAccessException, DuplicatedNameException {

		Authenticated newAuth = new Authenticated();

		newAuth.setId(competitionAdmin.getId());
		newAuth.setFirstName(competitionAdmin.getFirstName());
		newAuth.setLastName(competitionAdmin.getLastName());
		newAuth.setDni(competitionAdmin.getDni());
		newAuth.setTelephone(competitionAdmin.getTelephone());
		newAuth.setUser(competitionAdmin.getUser());
		newAuth.setEmail(competitionAdmin.getEmail());

		//Guardamos en la db el nuevo comp admin
		this.authenticatedService.saveAuthenticated(newAuth);

		this.authoritiesService.deleteAuthorities(competitionAdmin.getUser().getUsername(), "competitionAdmin");
		this.authoritiesService.saveAuthorities(competitionAdmin.getUser().getUsername(), "authenticated");
		this.competitionAdminRepository.delete(competitionAdmin);
	}

}
