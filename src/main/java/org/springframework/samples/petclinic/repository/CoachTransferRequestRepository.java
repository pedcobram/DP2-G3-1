
package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.samples.petclinic.model.CoachTransferRequest;

public interface CoachTransferRequestRepository {

	Collection<CoachTransferRequest> findAll();

	Collection<CoachTransferRequest> findAllOnHold();

	Collection<CoachTransferRequest> findAllReceivedRequests(int clubId);

	Collection<CoachTransferRequest> findAllByPresident(String presidentUsername);

	Collection<CoachTransferRequest> findAllByMyCoachId(int coachId);

	Collection<CoachTransferRequest> findAllByRequestedCoachId(int coachId);

	CoachTransferRequest findById(int id);

	CoachTransferRequest findByRequestedCoachIdAndMyCoachId(final int myCoachId, final int requestedCoachId);

	CoachTransferRequest findByMyCoachId(int coachId);

	CoachTransferRequest findByRequestedCoachId(int coachId);

	Integer count();

	void save(CoachTransferRequest coachTransferRequest);

	void delete(CoachTransferRequest coachTransferRequest);
}
