package org.springframework.samples.petclinic.domain.owner;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.samples.petclinic.domain.model.BaseModel;

@Getter
@SuperBuilder
public class Visit extends BaseModel {

	private LocalDate date;

	private String description;

}
