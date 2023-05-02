package org.springframework.samples.petclinic.infrastructure.controller.dto.model;

import jakarta.validation.constraints.NotEmpty;

public class PersonDto extends BaseDto {

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
