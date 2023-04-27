package org.springframework.samples.petclinic.infrastructure.persistence.mapper;

import java.util.Collection;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.infrastructure.persistence.vet.VetEntity;

@Mapper(componentModel = "spring")
public interface VetMapper {

	Collection<Vet> fromEntities(Collection<VetEntity> vetEntities);

	Vet fromEntitiesPage(VetEntity vetEntity);

	default Page<Vet> fromEntitiesPage(Page<VetEntity> vetEntitiesPage) {
		return vetEntitiesPage.map(this::fromEntitiesPage);
	}

}
