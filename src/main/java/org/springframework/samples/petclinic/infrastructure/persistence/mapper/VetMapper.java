package org.springframework.samples.petclinic.infrastructure.persistence.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.infrastructure.persistence.vet.VetEntity;

@Mapper
public interface VetMapper {

	List<Vet> from(List<VetEntity> content);

}
