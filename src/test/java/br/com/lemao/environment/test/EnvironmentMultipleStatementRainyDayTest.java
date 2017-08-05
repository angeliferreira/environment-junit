package br.com.lemao.environment.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.Description;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.environments.multiple.OneFemaleBikerWithBicycleEnvironment;
import br.com.lemao.environment.environments.multiple.OneMaleBikerWithBicycleEnvironment;
import br.com.lemao.environment.environments.multiple.TwoBicyclesEnvironment;
import br.com.lemao.environment.environments.multiple.TwoBikersWithBicyclesEnvironment;
import br.com.lemao.environment.junit.EnvironmentStatement;
import br.com.lemao.environment.junit.InMemoryRule;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;

public class EnvironmentMultipleStatementRainyDayTest {
	
	@Rule
	public InMemoryRule inMemoryRule = new InMemoryRule();
	
	@Test
	public void shouldThrowEnvironmentExceptionMultipleEnvironmentsWithGivenEnvironmentsAndGivenEnvironmentClass() {
		EnvironmentStatement environmentStatement = new EnvironmentStatement(null, Description.createSuiteDescription(FooEnvironmentClassTest.class));
		try {
			environmentStatement.evaluate();
			Assert.fail("should throw an EnvironmentException");
		} catch (Throwable e) {
			assertThat(e.getClass().getSimpleName(), is("EnvironmentException"));
			assertThat(e.getMessage(), is("@GivenEnvironments and @GivenEnvironment annotation in the same class or method !?"));
		} finally {
			assertTrue(BikerInMemorySupport.findAll().isEmpty());
			assertTrue(BicycleInMemorySupport.findAll().isEmpty());
		}
	}
	
	@Test
	public void shouldThrowEnvironmentExceptionMultipleEnvironmentsWithGivenEnvironmentsAndGivenEnvironmentMethod() {
		try {
			EnvironmentStatement environmentStatement = new EnvironmentStatement(null, createTestDescription());
			environmentStatement.evaluate();
			Assert.fail("should throw an EnvironmentException");
		} catch (Throwable e) {
			assertThat(e.getClass().getSimpleName(), is("EnvironmentException"));
			assertThat(e.getMessage(), is("@GivenEnvironments and @GivenEnvironment annotation in the same class or method !?"));
		} finally {
			assertTrue(BikerInMemorySupport.findAll().isEmpty());
			assertTrue(BicycleInMemorySupport.findAll().isEmpty());
		}
	}

	private Description createTestDescription() throws NoSuchMethodException, SecurityException {
		Class<FooEnvironmentMethodTest> testClass = FooEnvironmentMethodTest.class;
		String testNameMethod = "test";
		Method method = testClass.getMethod(testNameMethod);
		return Description.createTestDescription(testClass, testNameMethod, method.getAnnotations());
	}
	
	@GivenEnvironments(environments = {
			@GivenEnvironment(TwoBicyclesEnvironment.class),
			@GivenEnvironment(OneMaleBikerWithBicycleEnvironment.class),
			@GivenEnvironment(OneFemaleBikerWithBicycleEnvironment.class)
	})
	@GivenEnvironment(TwoBikersWithBicyclesEnvironment.class)
	private class FooEnvironmentClassTest {
		
	}
	
	private class FooEnvironmentMethodTest {
		@GivenEnvironments(environments = {
				@GivenEnvironment(TwoBicyclesEnvironment.class),
				@GivenEnvironment(OneMaleBikerWithBicycleEnvironment.class),
				@GivenEnvironment(OneFemaleBikerWithBicycleEnvironment.class)
		})
		@GivenEnvironment(TwoBikersWithBicyclesEnvironment.class)
		@Test
		public void test() {
			// Nothing to do here
		}
	}

}
