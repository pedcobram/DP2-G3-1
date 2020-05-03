
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.FootballClub;
import org.springframework.samples.petclinic.model.PlayerTransferRequest;
import org.springframework.samples.petclinic.repository.PlayerTransferRequestRepository;
import org.springframework.samples.petclinic.service.exceptions.MoneyClubException;
import org.springframework.samples.petclinic.service.exceptions.SalaryException;
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
	public Collection<PlayerTransferRequest> findPlayerTransferRequestsReceived(final int clubId) throws DataAccessException {
		return this.playerTransferRequestRepository.findReceivedRequests(clubId);
	}
	//
	@Transactional(readOnly = true)
	public PlayerTransferRequest findPlayerTransferRequestById(final int id) throws DataAccessException {
		return this.playerTransferRequestRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public PlayerTransferRequest findPlayerTransferRequestByPlayerId(final int playerId) throws DataAccessException {
		return this.playerTransferRequestRepository.findByPlayerId(playerId);
	}

	@Transactional(readOnly = true)
	public PlayerTransferRequest findOnlyByPlayerId(final int playerId) throws DataAccessException {
		return this.playerTransferRequestRepository.findOnlyByPlayerId(playerId);
	}

	@Transactional(readOnly = true)
	public PlayerTransferRequest findPlayerTransferRequestByPlayerIdAndStatusAccepted(final int playerId) throws DataAccessException {
		return this.playerTransferRequestRepository.findByPlayerIdAndStatusAccepted(playerId);
	}

	@Transactional(readOnly = true)
	public Integer countPlayerTransferRequestsByPresidentAndPlayer(final String presidentUsername, final int playerId) throws DataAccessException {
		return this.playerTransferRequestRepository.countByPresidentAndPlayer(presidentUsername, playerId);
	}
	
	@Transactional(readOnly = true)
	public Integer count() throws DataAccessException {
		return this.playerTransferRequestRepository.count();
	}

	@Transactional
	public void savePlayerTransferRequest(final PlayerTransferRequest playerTransferRequest) throws DataAccessException, MoneyClubException, TooManyPlayerRequestsException, SalaryException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();

		//RN: Fondos no pueden ser valor negativo tras la transacción
		FootballClub fc = this.footballClubService.findFootballClubByPresident(currentPrincipalName);
		Long moneyAfterTransaction = fc.getMoney() - playerTransferRequest.getOffer() - playerTransferRequest.getContract().getClause();

		if (moneyAfterTransaction < 0L) {
			throw new MoneyClubException();
		}

		//RN: Solo una peticion por presidente/jugador
		Integer count = this.countPlayerTransferRequestsByPresidentAndPlayer(currentPrincipalName, playerTransferRequest.getFootballPlayer().getId());

		if (count >= 1) {
			throw new TooManyPlayerRequestsException();
		}

		Integer valor = playerTransferRequest.getContract().getPlayer().getValue();
		Integer salario = valor / 10;

		//RN: Minimo y máximo
		if (playerTransferRequest.getOffer() < salario || playerTransferRequest.getOffer() > valor) {
			throw new SalaryException();
		}

		this.playerTransferRequestRepository.save(playerTransferRequest);
	}

	@Transactional
	public void updatePlayerTransferRequest(final PlayerTransferRequest playerTransferRequest) throws DataAccessException {
		this.playerTransferRequestRepository.save(playerTransferRequest);
	}

	public void deletePlayerTransferRequest(final PlayerTransferRequest playerTransferRequest) throws DataAccessException {
		playerTransferRequest.setClub(null);
		playerTransferRequest.setFootballPlayer(null);
		playerTransferRequest.setContract(null);
		this.playerTransferRequestRepository.delete(playerTransferRequest);
	}

}
