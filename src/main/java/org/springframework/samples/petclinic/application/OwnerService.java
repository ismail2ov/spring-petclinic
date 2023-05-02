package org.springframework.samples.petclinic.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.domain.model.PagedResult;
import org.springframework.samples.petclinic.domain.owner.Owner;
import org.springframework.samples.petclinic.domain.owner.OwnerRepository;
import org.springframework.samples.petclinic.domain.owner.PetType;

@RequiredArgsConstructor
public class OwnerService {

	private final OwnerRepository ownerRepository;

	public Owner findById(Integer ownerId) {
		return this.ownerRepository.findById(ownerId);
	}

	public void save(Owner owner) {
		this.ownerRepository.save(owner);
	}

	public PagedResult<Owner> findByLastName(String lastname, int page, int pageSize) {
		return this.ownerRepository.findByLastName(lastname, page, pageSize);
	}

	public List<PetType> findPetTypes() {
		return this.ownerRepository.findPetTypes();
	}

	public PagedResult<Owner> findAll(int page, int pageSize) {
		return this.ownerRepository.findAll(page, pageSize);
	}

}
