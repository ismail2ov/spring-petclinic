package org.springframework.samples.petclinic.infrastructure.controller.dto.model;

public class NamedDto extends BaseDto {

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
