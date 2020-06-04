
package org.springframework.samples.petclinic.repository;

import org.springframework.samples.petclinic.model.Calendary;

public interface CalendaryRepository {

	void delete(Calendary Calendary);

	Calendary findById(int i);

	Calendary findCalendaryByCompetitionId(int competitionId);

	void save(Calendary calendary);
}
