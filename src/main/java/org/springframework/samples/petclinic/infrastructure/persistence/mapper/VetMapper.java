package org.springframework.samples.petclinic.infrastructure.persistence.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.infrastructure.persistence.vet.VetEntity;

@Mapper
public interface VetMapper {

	Collection<Vet> fromEntities(Collection<VetEntity> vetEntities);

	List<Vet> from(List<VetEntity> vetEntities);

}
