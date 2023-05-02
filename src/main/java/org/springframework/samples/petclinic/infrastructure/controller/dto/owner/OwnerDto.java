package org.springframework.samples.petclinic.infrastructure.controller.dto.owner;

import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.infrastructure.controller.dto.model.PersonDto;
import org.springframework.util.Assert;

public class OwnerDto extends PersonDto {

	@NotEmpty
	private String address;

	@NotEmpty
	private String city;

	@NotEmpty
	private String telephone;

	private List<PetDto> pets = new ArrayList<>();

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<PetDto> getPets() {
		return this.pets;
	}

	public void addPet(PetDto pet) {
		if (pet.isNew()) {
			getPets().add(pet);
		}
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * @param name to test
	 * @return a pet if pet name is already in use
	 */
	public PetDto getPet(String name) {
		return getPet(name, false);
	}

	/**
	 * Return the Pet with the given id, or null if none found for this Owner.
	 * @param id to test
	 * @return a pet if pet id is already in use
	 */
	public PetDto getPet(Integer id) {
		for (PetDto pet : getPets()) {
			if (!pet.isNew()) {
				Integer compId = pet.getId();
				if (compId.equals(id)) {
					return pet;
				}
			}
		}
		return null;
	}

	/**
	 * Return the Pet with the given name, or null if none found for this Owner.
	 * @param name to test
	 * @return a pet if pet name is already in use
	 */
	public PetDto getPet(String name, boolean ignoreNew) {
		name = name.toLowerCase();
		for (PetDto pet : getPets()) {
			if (!ignoreNew || !pet.isNew()) {
				String compName = pet.getName();
				compName = compName == null ? "" : compName.toLowerCase();
				if (compName.equals(name)) {
					return pet;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this).append("id", this.getId())
			.append("new", this.isNew())
			.append("lastName", this.getLastName())
			.append("firstName", this.getFirstName())
			.append("address", this.address)
			.append("city", this.city)
			.append("telephone", this.telephone)
			.toString();
	}

	/**
	 * Adds the given {@link VisitDto} to the {@link PetDto} with the given identifier.
	 * @param petId the identifier of the {@link PetDto}, must not be {@literal null}.
	 * @param visitDto the visit to add, must not be {@literal null}.
	 */
	public void addVisit(Integer petId, VisitDto visitDto) {

		Assert.notNull(petId, "Pet identifier must not be null!");
		Assert.notNull(visitDto, "Visit must not be null!");

		PetDto pet = getPet(petId);

		Assert.notNull(pet, "Invalid Pet identifier!");

		pet.addVisit(visitDto);
	}

}
