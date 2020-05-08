
package org.springframework.samples.petclinic.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Calendary;

public interface CalendaryRepository {

	void delete(Calendary Calendary) throws DataAccessException;
}
