package org.springframework.samples.petclinic.domain.owner;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OwnerRepository {

	Owner findById(Integer ownerId);

	void save(Owner owner);

	Page<Owner> findByLastName(String lastname, Pageable pageable);

	List<PetType> findPetTypes();

	Page<Owner> findAll(Pageable pageable);

}
