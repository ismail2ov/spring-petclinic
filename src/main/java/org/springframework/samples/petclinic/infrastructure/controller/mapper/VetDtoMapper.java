package org.springframework.samples.petclinic.infrastructure.controller.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.infrastructure.view.VetDto;

@Mapper
public interface VetDtoMapper {

	List<VetDto> from(List<Vet> list);

}
