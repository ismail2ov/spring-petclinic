/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.infrastructure.controller.owner;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.assertj.core.util.Lists;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.samples.petclinic.application.OwnerService;
import org.springframework.samples.petclinic.domain.model.PagedResult;
import org.springframework.samples.petclinic.domain.owner.Owner;
import org.springframework.samples.petclinic.domain.owner.Pet;
import org.springframework.samples.petclinic.domain.owner.PetType;
import org.springframework.samples.petclinic.domain.owner.Visit;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.PetDto;
import org.springframework.samples.petclinic.infrastructure.controller.mapper.OwnerDtoMapperImpl;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */
@WebMvcTest(OwnerController.class)
@Import(OwnerDtoMapperImpl.class)
class OwnerControllerTests {

	private static final int TEST_OWNER_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OwnerService ownerService;

	private Owner george() {
		return george(Optional.empty());
	}

	private Owner george(Optional<Visit> visit) {
		PetType dog = PetType.builder().name("dog").build();

		Pet max = Pet.builder()
			.id(1)
			.type(dog)
			.name("Max")
			.birthDate(LocalDate.now())
			.visits(visit.map(Set::of).orElse(Collections.emptySet()))
			.build();

		return Owner.builder()
			.id(TEST_OWNER_ID)
			.firstName("George")
			.lastName("Franklin")
			.address("110 W. Liberty St.")
			.city("Madison")
			.telephone("6085551023")
			.pets(List.of(max))
			.build();
	}

	;

	@BeforeEach
	void setup() {
		Visit visit = Visit.builder().date(LocalDate.now()).build();
		Owner george = george(Optional.of(visit));

		given(this.ownerService.findByLastName(eq("Franklin"), anyInt(), anyInt()))
			.willReturn(new PagedResult<>(Lists.newArrayList(george), 2));

		given(this.ownerService.findAll(anyInt(), anyInt()))
			.willReturn(new PagedResult<Owner>(Lists.newArrayList(george), 1));

		given(this.ownerService.findById(TEST_OWNER_ID)).willReturn(george);

	}

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("ownerDto"))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc
			.perform(post("/owners/new").param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("address", "123 Caramel Street")
				.param("city", "London")
				.param("telephone", "01316761638"))
			.andExpect(status().is3xxRedirection());
	}

	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc
			.perform(post("/owners/new").param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("ownerDto"))
			.andExpect(model().attributeHasFieldErrors("ownerDto", "address"))
			.andExpect(model().attributeHasFieldErrors("ownerDto", "telephone"))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/owners/find"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("ownerDto"))
			.andExpect(view().name("owners/findOwners"));
	}

	@Test
	void testProcessFindFormSuccess() throws Exception {
		PagedResult<Owner> tasks = new PagedResult<>(Lists.newArrayList(george(), Owner.builder().build()), 2);
		Mockito.when(this.ownerService.findByLastName(anyString(), anyInt(), anyInt())).thenReturn(tasks);
		mockMvc.perform(get("/owners?page=1")).andExpect(status().isOk()).andExpect(view().name("owners/ownersList"));
	}

	@Test
	void testProcessFindFormByLastName() throws Exception {
		PagedResult<Owner> tasks = new PagedResult<>(Lists.newArrayList(george()), 1);
		Mockito.when(this.ownerService.findByLastName(eq("Franklin"), anyInt(), anyInt())).thenReturn(tasks);
		mockMvc.perform(get("/owners?page=1").param("lastName", "Franklin"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID));
	}

	@Test
	void testProcessFindFormNoOwnersFound() throws Exception {
		PagedResult<Owner> tasks = new PagedResult<>(Lists.newArrayList(), 0);
		Mockito.when(this.ownerService.findByLastName(eq("Unknown Surname"), anyInt(), anyInt())).thenReturn(tasks);
		mockMvc.perform(get("/owners?page=1").param("lastName", "Unknown Surname"))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasFieldErrors("ownerDto", "lastName"))
			.andExpect(model().attributeHasFieldErrorCode("ownerDto", "lastName", "notFound"))
			.andExpect(view().name("owners/findOwners"));

	}

	@Test
	void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("ownerDto"))
			.andExpect(model().attribute("ownerDto", hasProperty("lastName", is("Franklin"))))
			.andExpect(model().attribute("ownerDto", hasProperty("firstName", is("George"))))
			.andExpect(model().attribute("ownerDto", hasProperty("address", is("110 W. Liberty St."))))
			.andExpect(model().attribute("ownerDto", hasProperty("city", is("Madison"))))
			.andExpect(model().attribute("ownerDto", hasProperty("telephone", is("6085551023"))))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc
			.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("address", "123 Caramel Street")
				.param("city", "London")
				.param("telephone", "01616291589"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	void testProcessUpdateOwnerFormUnchangedSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc
			.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("address", "")
				.param("telephone", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("ownerDto"))
			.andExpect(model().attributeHasFieldErrors("ownerDto", "address"))
			.andExpect(model().attributeHasFieldErrors("ownerDto", "telephone"))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID))
			.andExpect(status().isOk())
			.andExpect(model().attribute("ownerDto", hasProperty("lastName", is("Franklin"))))
			.andExpect(model().attribute("ownerDto", hasProperty("firstName", is("George"))))
			.andExpect(model().attribute("ownerDto", hasProperty("address", is("110 W. Liberty St."))))
			.andExpect(model().attribute("ownerDto", hasProperty("city", is("Madison"))))
			.andExpect(model().attribute("ownerDto", hasProperty("telephone", is("6085551023"))))
			.andExpect(model().attribute("ownerDto", hasProperty("pets", not(empty()))))
			.andExpect(model().attribute("ownerDto", hasProperty("pets", new BaseMatcher<List<PetDto>>() {

				@Override
				public boolean matches(Object item) {
					@SuppressWarnings("unchecked")
					List<PetDto> pets = (List<PetDto>) item;
					PetDto pet = pets.get(0);
					if (pet.getVisits().isEmpty()) {
						return false;
					}
					return true;
				}

				@Override
				public void describeTo(Description description) {
					description.appendText("Max did not have any visits");
				}
			})))
			.andExpect(view().name("owners/ownerDetails"));
	}

}
