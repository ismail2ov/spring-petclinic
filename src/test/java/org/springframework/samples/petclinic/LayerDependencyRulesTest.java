package org.springframework.samples.petclinic;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

/**
 * For other usage examples * @see <a href=
 * "https://github.com/TNG/ArchUnit-Examples/blob/fe7e56f399d364fe1d71fb39629fc2b58489b5ee/example-plain/src/test/java/com/tngtech/archunit/exampletest/LayerDependencyRulesTest.java">LayerDependencyRulesTest</a>
 */
class LayerDependencyRulesTest {

	public static final String BASE_PACKAGE = "org.springframework.samples.petclinic";

	public static final String APPLICATION_PACKAGE = BASE_PACKAGE + ".application..";

	public static final String DOMAIN_PACKAGE = BASE_PACKAGE + ".domain..";

	private final JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

	private static final String JAVA_STANDARD_LIBRARY = "java..";

	@Test
	void applicationLayerShouldDependOnDomainLayerAndJavaStandardLibrary() {

		ArchRule rule = noClasses().that()
			.resideInAPackage(APPLICATION_PACKAGE)
			.should()
			.dependOnClassesThat()
			.resideOutsideOfPackages(APPLICATION_PACKAGE, DOMAIN_PACKAGE, JAVA_STANDARD_LIBRARY);

		rule.check(importedClasses);
	}

	@Test
	void domainLayerShouldDependOnJavaStandardLibrary() {

		ArchRule rule = noClasses().that()
			.resideInAPackage(DOMAIN_PACKAGE)
			.should()
			.dependOnClassesThat()
			.resideOutsideOfPackages(DOMAIN_PACKAGE, JAVA_STANDARD_LIBRARY);

		rule.check(importedClasses);
	}

}
