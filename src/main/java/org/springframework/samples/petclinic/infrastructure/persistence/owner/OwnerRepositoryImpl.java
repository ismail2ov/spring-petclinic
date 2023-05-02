package org.springframework.samples.petclinic.infrastructure.persistence.owner;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.domain.owner.Owner;
import org.springframework.samples.petclinic.domain.owner.OwnerRepository;
import org.springframework.samples.petclinic.domain.owner.PetType;
import org.springframework.samples.petclinic.infrastructure.persistence.mapper.OwnerMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OwnerRepositoryImpl implements OwnerRepository {

	private final OwnerDataRepository ownerDataRepository;

	private final OwnerMapper ownerMapper;

	@Override
	public Owner findById(Integer ownerId) {
		return this.ownerMapper.from(this.ownerDataRepository.findById(ownerId));
	}

	@Override
	public void save(Owner owner) {
		this.ownerDataRepository.save(this.ownerMapper.to(owner));
	}

	@Override
	public Page<Owner> findByLastName(String lastname, Pageable pageable) {
		return this.ownerMapper.from(this.ownerDataRepository.findByLastName(lastname, pageable));
	}

	@Override
	public List<PetType> findPetTypes() {
		return this.ownerMapper.from(this.ownerDataRepository.findPetTypes());
	}

	@Override
	public Page<Owner> findAll(Pageable pageable) {
		return this.ownerMapper.from(this.ownerDataRepository.findAll(pageable));
	}

}
