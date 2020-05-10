
package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Round;

public interface RoundRepository extends CrudRepository<Round, Integer> {

	List<Round> findByCompetitionId(int id);

}
