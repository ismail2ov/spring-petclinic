package org.springframework.samples.petclinic.domain.owner;

import java.util.List;
import org.springframework.samples.petclinic.domain.model.PagedResult;

public interface OwnerRepository {

	Owner findById(Integer ownerId);

	void save(Owner owner);

	PagedResult<Owner> findByLastName(String lastname, int page, int pageSize);

	List<PetType> findPetTypes();

	PagedResult<Owner> findAll(int page, int pageSize);

}
