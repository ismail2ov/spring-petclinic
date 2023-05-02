package org.springframework.samples.petclinic.infrastructure.controller.dto.owner;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import org.springframework.samples.petclinic.infrastructure.controller.dto.model.BaseDto;

public class VisitDto extends BaseDto {

	private LocalDate date;

	@NotEmpty
	private String description;

	public VisitDto() {
		this.date = LocalDate.now();
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
