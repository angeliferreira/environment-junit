package br.com.lemao.environment.test;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;

import br.com.lemao.environment.junit.EnvironmentRule;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

public class EnvironmentWithNoEnvironmentTest {

	@Rule
	public EnvironmentRule environmentRule = new EnvironmentRule();
	
	@Test
	public void oneBikerAndOneBicycleForThisBikerCreatedByEnvironment() {
		assertTrue(BikerInMemorySupport.findAll().isEmpty());
		assertTrue(BicycleInMemorySupport.findAll().isEmpty());
	}

}
