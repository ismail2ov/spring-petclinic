package org.springframework.samples.petclinic.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.application.OwnerService;
import org.springframework.samples.petclinic.domain.owner.OwnerRepository;

@Configuration
class OwnerConfiguration {

	@Bean
	public OwnerService ownerService(OwnerRepository ownerRepository) {
		return new OwnerService(ownerRepository);
	}

}
