package org.springframework.samples.petclinic.infrastructure.persistence.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.domain.owner.Owner;
import org.springframework.samples.petclinic.domain.owner.PetType;
import org.springframework.samples.petclinic.infrastructure.persistence.owner.OwnerEntity;
import org.springframework.samples.petclinic.infrastructure.persistence.owner.PetTypeEntity;

@Mapper
public interface OwnerMapper {

	Owner from(OwnerEntity ownerEntity);

	OwnerEntity to(Owner owner);

	default Page<Owner> from(Page<OwnerEntity> ownerEntityPage) {
		return ownerEntityPage.map(this::from);
	}

	List<PetType> from(List<PetTypeEntity> petTypes);

}
