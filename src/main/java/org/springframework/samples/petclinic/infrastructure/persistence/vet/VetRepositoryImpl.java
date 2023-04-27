package org.springframework.samples.petclinic.infrastructure.persistence.vet;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.samples.petclinic.domain.VetRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VetRepositoryImpl implements VetRepository {

	private final VetDataRepository vetRepository;

	public VetRepositoryImpl(VetDataRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Override
	public Page<Vet> findAllBy(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return this.vetRepository.findAll(pageable);
	}

	@Override
	public Collection<Vet> findAll() {
		return this.vetRepository.findAll();
	}

}
