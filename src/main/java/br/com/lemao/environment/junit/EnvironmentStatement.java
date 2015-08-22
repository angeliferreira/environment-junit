package br.com.lemao.environment.junit;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.IgnoreEnvironment;
import br.com.lemao.environment.exception.EnvironmentException;
import br.com.lemao.environment.executor.EnvironmentExecutor;

public class EnvironmentStatement extends Statement {

	private Description description;
	private Statement statement;

	public EnvironmentStatement(Statement statement, Description description) {
		this.statement = statement;
		this.description = description;
	}

	protected void before() {
		GivenEnvironment givenEnvironment = getGivenEnvironmentAnnotation();

		if (givenEnvironment == null || getIgnoreEnvironmentAnnotation() != null) return;

		runEnvironment(givenEnvironment);
	}

	protected void after() {
		// Nothing to do here
	}

	private void runEnvironment(GivenEnvironment givenEnvironment) {
		EnvironmentExecutor.gimme().execute(givenEnvironment);
	}
	
	private IgnoreEnvironment getIgnoreEnvironmentAnnotation() {
		return description.getAnnotation(IgnoreEnvironment.class);
	}

	private GivenEnvironment getGivenEnvironmentAnnotation() {
		GivenEnvironment givenEnvironmentMethodAnnotation = description.getAnnotation(GivenEnvironment.class);

		if (givenEnvironmentMethodAnnotation != null) return givenEnvironmentMethodAnnotation;

		return description.getTestClass().getAnnotation(GivenEnvironment.class);
	}

	@Override
	public void evaluate() throws Throwable {
		try {
			before();
			statement.evaluate();
		} catch (Exception e) {
			throw new EnvironmentException(getGivenEnvironmentAnnotation().getClass(), getGivenEnvironmentAnnotation().environmentName(), e);
		} finally {
			after();
		}
	}

}
