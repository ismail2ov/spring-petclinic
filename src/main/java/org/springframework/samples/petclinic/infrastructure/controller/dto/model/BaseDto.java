package org.springframework.samples.petclinic.infrastructure.controller.dto.model;

import java.io.Serializable;

public class BaseDto implements Serializable {

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isNew() {
		return this.id == null;
	}

}
