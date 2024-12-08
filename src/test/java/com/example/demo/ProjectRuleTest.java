package com.example.demo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.syntax.elements.ClassesShouldConjunction;
import com.tngtech.archunit.lang.syntax.elements.MethodsShouldConjunction;
import com.tngtech.archunit.library.Architectures;
import java.util.List;
import org.junit.jupiter.api.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProjectRuleTest {
	private static JavaClasses classes;
	private static final String[] DOMAINS = {};
	private static final String defaultPackage = "com.example";
	private static final String defaultPackageUnder = defaultPackage + "..";

	@BeforeAll
	public static void setUp() {
		classes = new ClassFileImporter().importPackages(defaultPackage);
	}

	@Test
	public void repository_모듈은_도메인의_repository_패키지에서만_참조한다() {
		ClassesShouldConjunction rule =
				noClasses()
						.that()
						.resideInAPackage(defaultPackageUnder)
						.and()
						.resideOutsideOfPackage("..repository..")
						.should()
						.dependOnClassesThat()
						.resideInAPackage(defaultPackage + ".repository..");

		rule.check(classes);
	}

	@Test
	public void Usecase_레이어는_Controller_레이어에서만_접근_가능하다() {
		Architectures.LayeredArchitecture rule =
				layeredArchitecture()
						.layer("Controller")
						.definedBy(defaultPackageUnder + "controller..")
						.layer("Usecase")
						.definedBy(defaultPackageUnder + "usecase..")
						.whereLayer("Usecase")
						.mayOnlyBeAccessedByLayers("Controller");

		rule.check(classes);
	}

	@Test
	public void 도메인_레이어는_독립적으로_구성되어야한다() {
		for (String domain : DOMAINS) {
			Architectures.LayeredArchitecture rule =
					layeredArchitecture()
							.layer("Domain")
							.definedBy(defaultPackage + "." + domain.toLowerCase() + "..")
							.whereLayer("Domain")
							.mayNotBeAccessedByAnyLayer();

			rule.check(classes);
		}
	}

	@Test
	public void UseCase_클래스는_트랜잭션이_선언되어_있어야_한다() {
		MethodsShouldConjunction rule =
				methods()
						.that()
						.areDeclaredInClassesThat()
						.resideInAPackage(defaultPackageUnder + "usecase..")
						.and()
						.arePublic()
						.and()
						.haveNameMatching(".*execute.*")
						.should()
						.beAnnotatedWith(Transactional.class);

		rule.check(classes);
	}

	@Test
	public void UseCase_클래스는_Component_어노테이션이_선언되어_있어야_한다() {
		ClassesShouldConjunction rule =
				classes()
						.that()
						.resideInAPackage(defaultPackageUnder + "usecase..")
						.should()
						.beAnnotatedWith(Component.class);

		rule.check(classes);
	}

	@Test
	public void Service_클래스는_Service_어노테이션이_선언되어_있어야_한다() {
		ClassesShouldConjunction rule =
				classes()
						.that()
						.resideInAPackage(defaultPackageUnder + "service..")
						.should()
						.beAnnotatedWith(Service.class);

		rule.check(classes);
	}

	@Test
	public void 도메인_모듈의_Repository_클래스는_Repository_어노테이션이_선언되어_있어야_한다() {
		for (String domain : DOMAINS) {
			ClassesShouldConjunction rule =
					classes()
							.that()
							.resideInAPackage(defaultPackage + "." + domain.toLowerCase() + ".repository")
							.and()
							.areNotInterfaces()
							.should()
							.beAnnotatedWith(Repository.class);

			rule.check(classes);
		}
	}

	@Test
	public void 도메인_모듈의_Repository_클래스는_repository_패키지에_선언되어_있어야_한다() {
		for (String domain : DOMAINS) {
			ClassesShouldConjunction rule =
					classes()
							.that()
							.areAnnotatedWith(Repository.class)
							.and()
							.haveSimpleNameStartingWith(domain)
							.should()
							.resideInAPackage(defaultPackage + "." + domain.toLowerCase() + ".repository");

			rule.check(classes);
		}
	}

	@Test
	public void 도메인_모델은_도메인_ID를_가지고_있어야_한다() {
		for (String domain : DOMAINS) {
			JavaClasses domainClasses =
					new ClassFileImporter()
							.importPackages(defaultPackage + "." + domain.toLowerCase() + ".model");
			for (JavaClass domainClass : domainClasses) {
				String simpleName = domainClass.getSimpleName();
				if (domainClass.isEnum()) {
					continue;
				}
				if (simpleName.startsWith(domain)) {
					JavaField id = domainClass.getField("id");
					Assertions.assertNotNull(id, domainClass.getName() + " has no id field");
					Assertions.assertEquals(
							"java.lang.Long",
							id.getType().getName(),
							domainClass.getName() + " id field type is not Long");
					Assertions.assertTrue(
							id.getModifiers().contains(JavaModifier.FINAL),
							domainClass.getName() + " id field is not final");
				}
			}
		}
	}

	@Test
	public void 도메인_model_패키지_안에_있는_도메인_모델이_아닌_클래스는_ID를_가지고_있어서는_안된다() {
		for (String domain : DOMAINS) {
			JavaClasses domainClasses =
					new ClassFileImporter()
							.importPackages(defaultPackage + "." + domain.toLowerCase() + ".model");
			for (JavaClass domainClass : domainClasses) {
				String simpleName = domainClass.getSimpleName();
				if (domainClass.isEnum()) {
					continue;
				}
				if (!simpleName.startsWith(domain)) {
					List<String> fields =
							domainClass.getAllFields().stream().map(JavaField::getName).toList();
					Assertions.assertFalse(fields.contains("id"), domainClass.getName() + " has id field");
				}
			}
		}
	}
}
