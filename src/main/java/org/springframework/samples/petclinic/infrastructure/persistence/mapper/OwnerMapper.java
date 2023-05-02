package org.springframework.samples.petclinic.infrastructure.persistence.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.samples.petclinic.domain.owner.Owner;
import org.springframework.samples.petclinic.domain.owner.PetType;
import org.springframework.samples.petclinic.infrastructure.persistence.owner.OwnerEntity;
import org.springframework.samples.petclinic.infrastructure.persistence.owner.PetTypeEntity;

@Mapper
public interface OwnerMapper {

	Owner from(OwnerEntity ownerEntity);

	OwnerEntity to(Owner owner);

	List<Owner> from(List<OwnerEntity> ownerEntities);

	List<PetType> petTypesFrom(List<PetTypeEntity> petTypes);

}
