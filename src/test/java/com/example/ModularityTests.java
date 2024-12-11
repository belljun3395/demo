package com.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class ModularityTests {
	static ApplicationModules modules;

	@BeforeAll
	public static void setup() {
		modules = ApplicationModules.of(ApiApplication.class);
	}

	@Test
	void verifyModularity() {
		modules.verify();
	}

	@Test
	void verifyLayering() {
		new Documenter(modules).writeDocumentation().writeIndividualModulesAsPlantUml();
		;
	}
}
