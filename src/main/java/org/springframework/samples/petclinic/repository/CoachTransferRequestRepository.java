
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.CoachTransferRequest;

public interface CoachTransferRequestRepository {

	Collection<CoachTransferRequest> findAll() throws DataAccessException;

	Collection<CoachTransferRequest> findAllOnHold() throws DataAccessException;

	Collection<CoachTransferRequest> findAllReceivedRequests(int clubId) throws DataAccessException;

	Collection<CoachTransferRequest> findAllByPresident(String presidentUsername) throws DataAccessException;

	Collection<CoachTransferRequest> findAllByMyCoachId(int coachId) throws DataAccessException;

	Collection<CoachTransferRequest> findAllByRequestedCoachId(int coachId) throws DataAccessException;

	CoachTransferRequest findById(int id) throws DataAccessException;

	CoachTransferRequest findByRequestedCoachIdAndMyCoachId(final int myCoachId, final int requestedCoachId);

	CoachTransferRequest findByMyCoachId(int coachId) throws DataAccessException;

	CoachTransferRequest findByRequestedCoachId(int coachId) throws DataAccessException;

	Integer count() throws DataAccessException;

	void save(CoachTransferRequest coachTransferRequest) throws DataAccessException;

	void delete(CoachTransferRequest coachTransferRequest) throws DataAccessException;
}
