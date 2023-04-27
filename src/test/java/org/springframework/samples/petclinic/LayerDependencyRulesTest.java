package org.springframework.samples.petclinic;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

/*
 * Used the example of: https://github.com/TNG/ArchUnit-Examples/blob/fe7e56f399d364fe1d71fb39629fc2b58489b5ee/example-plain/src/test/java/com/tngtech/archunit/exampletest/LayerDependencyRulesTest.java
 */
class LayerDependencyRulesTest {

	private final JavaClasses importedClasses = new ClassFileImporter()
		.importPackages("org.springframework.samples.petclinic");

	@Test
	void applicationLayerShouldNotDependOnInfrastructureLayer() {
		ArchRule rule = noClasses().that()
			.resideInAPackage("..application..")
			.should()
			.dependOnClassesThat()
			.resideInAPackage("..infrastructure..");

		rule.check(importedClasses);
	}

}
