package org.springframework.samples.petclinic.infrastructure.persistence.owner;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.domain.model.PagedResult;
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
	public PagedResult<Owner> findByLastName(String lastname, int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<OwnerEntity> ownerEntities = this.ownerDataRepository.findByLastName(lastname, pageable);

		List<Owner> ownerList = this.ownerMapper.from(ownerEntities.getContent());

		return new PagedResult<>(ownerList, ownerEntities.getTotalElements());
	}

	@Override
	public List<PetType> findPetTypes() {
		return this.ownerMapper.petTypesFrom(this.ownerDataRepository.findPetTypes());
	}

	@Override
	public PagedResult<Owner> findAll(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<OwnerEntity> ownerEntities = this.ownerDataRepository.findAll(pageable);

		List<Owner> ownerList = this.ownerMapper.from(ownerEntities.getContent());

		return new PagedResult<>(ownerList, ownerEntities.getTotalElements());
	}

}
