package org.springframework.samples.petclinic.domain.owner;

import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.samples.petclinic.domain.model.PersonModel;

@Getter
@SuperBuilder
public class Owner extends PersonModel {

	private String address;

	private String city;

	private String telephone;

	private List<Pet> pets;

}
