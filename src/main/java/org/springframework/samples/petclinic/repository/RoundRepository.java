
package org.springframework.samples.petclinic.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Round;

public interface RoundRepository extends CrudRepository<Round, Integer> {

}
