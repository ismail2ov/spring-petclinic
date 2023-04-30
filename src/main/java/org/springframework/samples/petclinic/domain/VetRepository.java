package org.springframework.samples.petclinic.domain;

import java.util.List;
import org.springframework.samples.petclinic.domain.model.PagedResult;
import org.springframework.samples.petclinic.domain.vet.Vet;

public interface VetRepository {

	PagedResult<Vet> findAllBy(int page, int pageSize);

	List<Vet> findAll();

}
