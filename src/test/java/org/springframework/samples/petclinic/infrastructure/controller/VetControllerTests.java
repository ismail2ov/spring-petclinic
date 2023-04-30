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

package org.springframework.samples.petclinic.infrastructure.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Set;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.application.VetService;
import org.springframework.samples.petclinic.domain.model.PagedResult;
import org.springframework.samples.petclinic.domain.vet.Specialty;
import org.springframework.samples.petclinic.domain.vet.Vet;
import org.springframework.samples.petclinic.infrastructure.controller.mapper.VetDtoMapperImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Test class for the {@link VetController}
 */

@WebMvcTest(VetController.class)
@Import(VetDtoMapperImpl.class)
class VetControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VetService vetService;

	private Vet james() {
		return Vet.builder().id(1).firstName("James").lastName("Carter").build();
	}

	private Vet helen() {
		Specialty radiology = Specialty.builder().id(1).name("radiology").build();

		return Vet.builder().id(2).firstName("Helen").lastName("Leary").specialties(Set.of(radiology)).build();
	}

	@BeforeEach
	void setup() {
		ArrayList<Vet> vetsList = Lists.newArrayList(james(), helen());

		given(this.vetService.getVets()).willReturn(vetsList);

		PagedResult<Vet> vetsPage = new PagedResult<>(vetsList, 6);
		given(this.vetService.getVetPage(0, 5)).willReturn(vetsPage);
	}

	@Test
	void testShowVetListHtml() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/vets.html?page=1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("listVets"))
			.andExpect(view().name("vets/vetList"));

	}

	@Test
	void testShowResourcesVetList() throws Exception {
		ResultActions actions = mockMvc.perform(get("/vets").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.vetList[0].id").value(1));
	}

}
