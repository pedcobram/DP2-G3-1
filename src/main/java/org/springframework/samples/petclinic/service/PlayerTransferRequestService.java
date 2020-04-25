
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.repository.PlayerTransferRequestRepository;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.TooManyPlayerRequestsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerTransferRequestService {

	private PlayerTransferRequestRepository	playerTransferRequestRepository;

	@Autowired
	private FootballClubService				footballClubService;


	@Autowired
	public PlayerTransferRequestService(final PlayerTransferRequestRepository playerTransferRequestRepository) {
		this.playerTransferRequestRepository = playerTransferRequestRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PlayerTransferRequest> findPlayerTransferRequest() throws DataAccessException {
		return this.playerTransferRequestRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Collection<PlayerTransferRequest> findPlayerTransferRequestByPresident(final String userName) throws DataAccessException {
		return this.playerTransferRequestRepository.findByPresident(userName);
	}

	@Transactional(readOnly = true)
	public Collection<PlayerTransferRequest> findPlayerTransferRequestByFootballPlayer(final String footballPlayerName) throws DataAccessException {
		return this.playerTransferRequestRepository.findByFootballPlayer(footballPlayerName);
	}

	@Transactional(readOnly = true)
	public PlayerTransferRequest findPlayerTransferRequestById(final int id) throws DataAccessException {
		return this.playerTransferRequestRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Integer countPlayerTransferRequestsByPresidentAndPlayer(final String presidentUsername, final int playerId) throws DataAccessException {
		return this.playerTransferRequestRepository.countByPresidentAndPlayer(presidentUsername, playerId);
	}

	@Transactional
	public void savePlayerTransferRequest(final PlayerTransferRequest playerTransferRequest) throws DataAccessException, MoneyClubException, TooManyPlayerRequestsException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//RN: Fondos no pueden ser valor negativo tras la transacción
		FootballClub fc = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Long moneyAfterTransaction = fc.getMoney() - playerTransferRequest.getPlayerValue();

		if (moneyAfterTransaction < 0) {
			throw new MoneyClubException();
		}

		//RN: Solo una peticion por presidente/jugador
		Integer count = this.countPlayerTransferRequestsByPresidentAndPlayer(currentPrincipalName, playerTransferRequest.getFootballPlayer().getId());

		if (count >= 1) {
			throw new TooManyPlayerRequestsException();
		}

		this.playerTransferRequestRepository.save(playerTransferRequest);
	}

	public void deletePlayerTransferRequest(final PlayerTransferRequest playerTransferRequest) throws DataAccessException {
		playerTransferRequest.setPresident(null);
		playerTransferRequest.setFootballPlayer(null);
		this.playerTransferRequestRepository.delete(playerTransferRequest);
	}

}
