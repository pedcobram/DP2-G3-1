
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Fan;

public interface FanRepository extends CrudRepository<Fan, Integer> {

	boolean existsByUserId(int auId);

	Fan findByUserId(int id);

}
