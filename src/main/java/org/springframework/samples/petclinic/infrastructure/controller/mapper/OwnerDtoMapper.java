package org.springframework.samples.petclinic.infrastructure.controller.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.OwnerDto;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.PetDto;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.PetTypeDto;
import org.springframework.samples.petclinic.infrastructure.persistence.owner.OwnerEntity;
import org.springframework.samples.petclinic.infrastructure.persistence.owner.PetEntity;
import org.springframework.samples.petclinic.infrastructure.persistence.owner.PetTypeEntity;
import org.springframework.samples.petclinic.infrastructure.view.VetDto;

@Mapper
public interface OwnerDtoMapper {

	OwnerDto from(OwnerEntity ownerEntity);

	OwnerEntity to(OwnerDto ownerDto);

	default Page<OwnerDto> from(Page<OwnerEntity> page) {
		return page.map(this::from);
	}

	Collection<PetTypeDto> from(List<PetTypeEntity> petTypes);

	PetDto from(PetEntity pet);

}
