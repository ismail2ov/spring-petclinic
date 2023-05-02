package org.springframework.samples.petclinic.domain.owner;

import java.time.LocalDate;
import java.util.Set;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.samples.petclinic.domain.model.NamedModel;

@Getter
@SuperBuilder
public class Pet extends NamedModel {

	private LocalDate birthDate;

	private PetType type;

	private Set<Visit> visits;

}
