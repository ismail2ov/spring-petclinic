package org.springframework.samples.petclinic.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.application.VetService;
import org.springframework.samples.petclinic.domain.VetRepository;

@Configuration
class VetConfiguration {

	@Bean
	public VetService vetService(VetRepository vetRepository) {
		return new VetService(vetRepository);
	}

}
