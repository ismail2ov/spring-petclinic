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

import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.application.OwnerService;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.OwnerDto;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.PetDto;
import org.springframework.samples.petclinic.infrastructure.controller.dto.owner.PetTypeDto;
import org.springframework.samples.petclinic.infrastructure.controller.mapper.OwnerDtoMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
@RequiredArgsConstructor
class PetController {

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";

	private final OwnerService ownerService;

	private final OwnerDtoMapper mapper;

	@ModelAttribute("types")
	public Collection<PetTypeDto> populatePetTypes() {
		return mapper.from(this.ownerService.findPetTypes());
	}

	@ModelAttribute("ownerDto")
	public OwnerDto findOwner(@PathVariable("ownerId") int ownerId) {
		return mapper.from(this.ownerService.findById(ownerId));
	}

	@ModelAttribute("petDto")
	public PetDto findPet(@PathVariable("ownerId") int ownerId,
			@PathVariable(name = "petId", required = false) Integer petId) {
		OwnerDto ownerDto = mapper.from(this.ownerService.findById(ownerId));
		return petId == null ? new PetDto() : ownerDto.getPet(petId);
	}

	@InitBinder("ownerDto")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("petDto")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping("/pets/new")
	public String initCreationForm(OwnerDto ownerDto, ModelMap model) {
		PetDto petDto = new PetDto();
		ownerDto.addPet(petDto);
		model.put("petDto", petDto);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/new")
	public String processCreationForm(OwnerDto ownerDto, @Valid PetDto petDto, BindingResult result, ModelMap model) {
		if (StringUtils.hasLength(petDto.getName()) && petDto.isNew()
				&& ownerDto.getPet(petDto.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}

		ownerDto.addPet(petDto);
		if (result.hasErrors()) {
			model.put("petDto", petDto);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}

		this.ownerService.save(mapper.to(ownerDto));
		return "redirect:/owners/{ownerId}";
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(OwnerDto ownerDto, @PathVariable("petId") int petId, ModelMap model) {
		PetDto petDto = ownerDto.getPet(petId);
		model.put("petDto", petDto);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(@Valid PetDto petDto, BindingResult result, OwnerDto ownerDto, ModelMap model) {
		if (result.hasErrors()) {
			model.put("petDto", petDto);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}

		ownerDto.addPet(petDto);
		this.ownerService.save(mapper.to(ownerDto));
		return "redirect:/owners/{ownerId}";
	}

}
