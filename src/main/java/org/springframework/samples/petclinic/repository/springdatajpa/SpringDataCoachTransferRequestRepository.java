
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.CoachTransferRequest;
import org.springframework.samples.petclinic.repository.CoachTransferRequestRepository;

public interface SpringDataCoachTransferRequestRepository extends CoachTransferRequestRepository, Repository<CoachTransferRequest, Integer> {

	@Override
	@Query("SELECT a from CoachTransferRequest a")
	Collection<CoachTransferRequest> findAll();

	@Override
	@Query("SELECT a from CoachTransferRequest a WHERE a.status = 0")
	Collection<CoachTransferRequest> findAllOnHold();

	@Override
	@Query("SELECT a from CoachTransferRequest a WHERE a.myCoach.id =:coachId AND a.status = 0")
	Collection<CoachTransferRequest> findAllReceivedRequests(@Param("coachId") int clubId);

	@Override
	@Query("SELECT a FROM CoachTransferRequest a WHERE a.myCoach.club.president.user.username =:presidentUsername AND a.status = 0")
	Collection<CoachTransferRequest> findAllByPresident(@Param("presidentUsername") String presidentUsername);

	@Override
	@Query("SELECT a FROM CoachTransferRequest a WHERE a.myCoach.id =:coachId AND a.status = 0")
	Collection<CoachTransferRequest> findAllByMyCoachId(@Param("coachId") int coachId);

	@Override
	@Query("SELECT a FROM CoachTransferRequest a WHERE a.requestedCoach.id =:coachId AND a.status = 0")
	Collection<CoachTransferRequest> findAllByRequestedCoachId(int coachId);

	@Override
	@Query("SELECT a FROM CoachTransferRequest a WHERE a.id =:id AND a.status = 0")
	CoachTransferRequest findById(@Param("id") int id);

	@Override
	@Query("SELECT a FROM CoachTransferRequest a WHERE a.myCoach.id =:myCoachId AND a.requestedCoach.id =:requestedCoachId AND a.status = 0")
	CoachTransferRequest findByRequestedCoachIdAndMyCoachId(final int myCoachId, final int requestedCoachId);

	@Override
	@Query("SELECT a FROM CoachTransferRequest a WHERE a.myCoach.id =:coachId AND a.status = 0")
	CoachTransferRequest findByMyCoachId(@Param("coachId") int coachId);

	@Override
	@Query("SELECT a FROM CoachTransferRequest a WHERE a.requestedCoach.id =:coachId AND a.status = 0")
	CoachTransferRequest findByRequestedCoachId(@Param("coachId") int coachId);

	@Override
	@Query("SELECT COUNT(a) FROM CoachTransferRequest a")
	Integer count();
}
