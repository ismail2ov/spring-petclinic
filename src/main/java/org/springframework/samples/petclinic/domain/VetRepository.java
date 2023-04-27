package org.springframework.samples.petclinic.domain;

import java.util.Collection;
import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.infrastructure.persistence.vet.Vet;

public interface VetRepository {

	Page<Vet> findAllBy(int page, int pageSize);

	Collection<Vet> findAll();

}
