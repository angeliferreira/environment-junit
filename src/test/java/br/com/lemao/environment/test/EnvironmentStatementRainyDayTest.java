package br.com.lemao.environment.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.environments.BikersAndBikesEnvironmentSet;
import br.com.lemao.environment.exception.EnvironmentException;
import br.com.lemao.environment.junit.EnvironmentStatement;
import br.com.lemao.environment.junit.InMemoryRule;
import br.com.lemao.environment.junit.InMemoryStatement;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

@GivenEnvironment(BikersAndBikesEnvironmentSet.TwoBikersWithBicyclesInMemory.class)
public class EnvironmentStatementRainyDayTest {
	
	@Rule
	public InMemoryRule inMemoryRule = new InMemoryRule();
	
	@Test
	public void shouldThrowEnvironmentExceptionExecutingEnvironmentStatement() {
		EnvironmentStatement environmentStatement = new EnvironmentStatement(null, Description.createSuiteDescription(getClass()));
		try {
			environmentStatement.evaluate();
			Assert.fail("should throw an EnvironmentException");
		} catch (EnvironmentException e) {
			assertThat(e.getClass().getSimpleName(), is("EnvironmentException"));
		} catch (Throwable e) {
			Assert.fail("should throw an EnvironmentException");
		} finally {
			assertFalse(BikerInMemorySupport.findAll().isEmpty());
			assertFalse(BicycleInMemorySupport.findAll().isEmpty());
			BikerInMemorySupport.dropObjects();
			BicycleInMemorySupport.dropObjects();
		}
	}
	
	@Test
	public void shouldThrowEnvironmentExceptionExecutingEnvironmentStatementAndExecuteAfterStatement() {
		assertFalse(BikerInMemorySupport.findAll().isEmpty());
		assertFalse(BicycleInMemorySupport.findAll().isEmpty());
		
		InMemoryStatement myStatement = new InMemoryStatement(null, Description.createSuiteDescription(getClass()));
		try {
			myStatement.evaluate();
			Assert.fail("should throw an EnvironmentException");
		} catch (EnvironmentException e) {
			assertThat(e.getClass().getSimpleName(), is("EnvironmentException"));
		} catch (Throwable e) {
			Assert.fail("should throw an EnvironmentException");
		}
		
		assertTrue(BikerInMemorySupport.findAll().isEmpty());
		assertTrue(BicycleInMemorySupport.findAll().isEmpty());
	}

}
