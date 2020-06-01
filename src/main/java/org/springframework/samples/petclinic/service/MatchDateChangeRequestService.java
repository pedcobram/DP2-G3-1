
package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.MatchDateChangeRequest;
import org.springframework.samples.petclinic.repository.MatchDateChangeRequestRepository;
import org.springframework.samples.petclinic.service.exceptions.AlreadyOneRequestOpenException;
import org.springframework.samples.petclinic.service.exceptions.IllegalDateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchDateChangeRequestService {

	private MatchDateChangeRequestRepository matchDateChangeRequestRepository;


	@Autowired
	public MatchDateChangeRequestService(final MatchDateChangeRequestRepository matchDateChangeRequestRepository) {
		this.matchDateChangeRequestRepository = matchDateChangeRequestRepository;
	}

	@Transactional
	public MatchDateChangeRequest findMatchDateChangeRequestById(final int id) {
		return this.matchDateChangeRequestRepository.findById(id);
	}

	@Transactional
	public Collection<MatchDateChangeRequest> findAllMatchDateChangeRequests(final String presidentUsername) {
		return this.matchDateChangeRequestRepository.findAllMatchDateChangeRequest(presidentUsername);
	}

	@Transactional
	public void saveMatchDateChangeRequest(final MatchDateChangeRequest matchDateChangeRequest) throws IllegalDateException, AlreadyOneRequestOpenException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//RN: Solo una petición por partido y presidente
		List<MatchDateChangeRequest> matchDateRequest = new ArrayList<>();
		matchDateRequest.addAll(this.findAllMatchDateChangeRequests(currentPrincipalName));

		for (MatchDateChangeRequest mdcr : matchDateRequest) {
			if (matchDateChangeRequest.getMatch().equals(mdcr.getMatch()) && matchDateChangeRequest.getRequest_creator().equals(mdcr.getRequest_creator())) {
				throw new AlreadyOneRequestOpenException();
			}
		}

		//RN: Fecha no puede ser igual o menor a la actual
		if (matchDateChangeRequest.getNew_date().before(matchDateChangeRequest.getMatch().getMatchDate()) || matchDateChangeRequest.getNew_date().compareTo(matchDateChangeRequest.getMatch().getMatchDate()) == 0) {
			throw new IllegalDateException();
		}

		this.matchDateChangeRequestRepository.save(matchDateChangeRequest);
	}

	@Transactional
	public void deleteMatchDateChangeRequest(final MatchDateChangeRequest matchDateChangeRequest) {
		matchDateChangeRequest.setMatch(null);
		this.matchDateChangeRequestRepository.delete(matchDateChangeRequest);
	}

}
