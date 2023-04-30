package org.springframework.samples.petclinic.infrastructure.persistence.vet;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.domain.VetRepository;
import org.springframework.samples.petclinic.domain.model.PagedResult;
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
	public PagedResult<Vet> findAllBy(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		Page<VetEntity> vetEntities = this.vetRepository.findAll(pageable);

		List<Vet> vetList = vetMapper.from(vetEntities.getContent());

		return new PagedResult<>(vetList, vetEntities.getTotalElements());
	}

	@Override
	public List<Vet> findAll() {
		List<VetEntity> vetEntities = this.vetRepository.findAll();
		return vetMapper.from(vetEntities);
	}

}
