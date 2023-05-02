package org.springframework.samples.petclinic.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<Owner> findByLastName(String lastname, Pageable pageable) {
		return this.ownerRepository.findByLastName(lastname, pageable);
	}

	public List<PetType> findPetTypes() {
		return this.ownerRepository.findPetTypes();
	}

	public Page<Owner> findAll(Pageable pageable) {
		return this.ownerRepository.findAll(pageable);
	}

}
