package org.springframework.samples.petclinic.domain;

import java.util.Collection;
import org.springframework.samples.petclinic.domain.model.PagedResult;
import org.springframework.samples.petclinic.domain.vet.Vet;

public interface VetRepository {

	PagedResult<Vet> findAllBy(int page, int pageSize);

	Collection<Vet> findAll();

}
