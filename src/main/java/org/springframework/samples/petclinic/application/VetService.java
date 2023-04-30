package org.springframework.samples.petclinic.application;

import java.util.Collection;
import org.springframework.samples.petclinic.domain.VetRepository;
import org.springframework.samples.petclinic.domain.model.PagedResult;
import org.springframework.samples.petclinic.domain.vet.Vet;

public class VetService {

	private final VetRepository vetRepository;

	public VetService(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	public PagedResult<Vet> getVetPage(int page, int pageSize) {
		return this.vetRepository.findAllBy(page, pageSize);
	}

	public Collection<Vet> getVets() {
		return this.vetRepository.findAll();
	}

}
