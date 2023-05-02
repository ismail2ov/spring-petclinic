/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.infrastructure.persistence.owner;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class for <code>Owner</code> domain objects All method names are compliant
 * with Spring Data naming conventions so this interface can easily be extended for Spring
 * Data. See:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
public interface OwnerDataRepository extends Repository<OwnerEntity, Integer> {

	/**
	 * Retrieve all {@link PetTypeEntity}s from the data store.
	 * @return a Collection of {@link PetTypeEntity}s.
	 */
	@Query("SELECT ptype FROM PetTypeEntity ptype ORDER BY ptype.name")
	@Transactional(readOnly = true)
	List<PetTypeEntity> findPetTypes();

	/**
	 * Retrieve {@link OwnerEntity}s from the data store by last name, returning all
	 * owners whose last name <i>starts</i> with the given name.
	 * @param lastName Value to search for
	 * @return a Collection of matching {@link OwnerEntity}s (or an empty Collection if
	 * none found)
	 */

	@Query("SELECT DISTINCT owner FROM OwnerEntity owner left join  owner.pets WHERE owner.lastName LIKE :lastName% ")
	@Transactional(readOnly = true)
	Page<OwnerEntity> findByLastName(@Param("lastName") String lastName, Pageable pageable);

	/**
	 * Retrieve an {@link OwnerEntity} from the data store by id.
	 * @param id the id to search for
	 * @return the {@link OwnerEntity} if found
	 */
	@Query("SELECT owner FROM OwnerEntity owner left join fetch owner.pets WHERE owner.id =:id")
	@Transactional(readOnly = true)
	OwnerEntity findById(@Param("id") Integer id);

	/**
	 * Save an {@link OwnerEntity} to the data store, either inserting or updating it.
	 * @param owner the {@link OwnerEntity} to save
	 */
	void save(OwnerEntity owner);

	/**
	 * Returns all the owners from data store
	 **/
	@Query("SELECT owner FROM OwnerEntity owner")
	@Transactional(readOnly = true)
	Page<OwnerEntity> findAll(Pageable pageable);

}
