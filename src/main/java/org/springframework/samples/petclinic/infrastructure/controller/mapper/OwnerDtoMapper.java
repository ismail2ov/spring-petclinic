package org.springframework.samples.petclinic.infrastructure.controller.mapper;

import java.util.Collection;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.domain.owner.Owner;
import org.springframework.samples.petclinic.domain.owner.Pet;
import org.springframework.samples.petclinic.domain.owner.PetType;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.OwnerDto;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.PetDto;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.PetTypeDto;

@Mapper
public interface OwnerDtoMapper {

	OwnerDto from(Owner owner);

	Owner to(OwnerDto ownerDto);

	default Page<OwnerDto> from(Page<Owner> page) {
		return page.map(this::from);
	}

	Collection<PetTypeDto> from(List<PetType> petTypes);

	PetDto from(Pet pet);

}
