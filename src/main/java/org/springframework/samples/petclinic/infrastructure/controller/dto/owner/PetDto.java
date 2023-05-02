package org.springframework.samples.petclinic.infrastructure.controller.dto.owner;

import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.samples.petclinic.infrastructure.controller.dto.model.NamedDto;

public class PetDto extends NamedDto {

	private LocalDate birthDate;

	private PetTypeDto type;

	private Set<VisitDto> visits = new LinkedHashSet<>();

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return this.birthDate;
	}

	public PetTypeDto getType() {
		return this.type;
	}

	public void setType(PetTypeDto type) {
		this.type = type;
	}

	public Collection<VisitDto> getVisits() {
		return this.visits;
	}

	public void addVisit(VisitDto visit) {
		getVisits().add(visit);
	}

}
