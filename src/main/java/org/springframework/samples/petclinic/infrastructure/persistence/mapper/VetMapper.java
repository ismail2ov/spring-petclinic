package org.springframework.samples.petclinic.infrastructure.persistence.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.samples.petclinic.domain.vet.Specialty;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.infrastructure.persistence.vet.SpecialtyEntity;
import org.springframework.samples.petclinic.infrastructure.persistence.vet.VetEntity;

@Mapper
public interface VetMapper {

	Collection<Vet> fromEntities(Collection<VetEntity> vetEntities);

	Specialty fromEntitiesPage(SpecialtyEntity vetEntity);

	Vet fromEntitiesPage(VetEntity vetEntity);

	List<Vet> from(List<VetEntity> content);

}
