package org.springframework.samples.petclinic.infrastructure.persistence.vet;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.domain.VetRepository;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.infrastructure.persistence.mapper.VetMapper;
import org.springframework.stereotype.Repository;

@Repository
public class VetRepositoryImpl implements VetRepository {

	private final VetDataRepository vetRepository;

	private final VetMapper vetMapper;

	public VetRepositoryImpl(VetDataRepository vetRepository, VetMapper vetMapper) {
		this.vetRepository = vetRepository;
		this.vetMapper = vetMapper;
	}

	@Override
	public Page<Vet> findAllBy(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<VetEntity> vetEntities = this.vetRepository.findAll(pageable);

		return vetMapper.fromEntitiesPage(vetEntities);
	}

	@Override
	public Collection<Vet> findAll() {
		Collection<VetEntity> vetEntities = this.vetRepository.findAll();
		return vetMapper.fromEntities(vetEntities);
	}

}
