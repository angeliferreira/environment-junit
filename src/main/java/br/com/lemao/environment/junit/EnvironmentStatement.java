package br.com.lemao.environment.junit;

import java.lang.annotation.Annotation;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.exception.EnvironmentException;
import br.com.lemao.environment.executor.EnvironmentExecutor;
import br.com.lemao.environment.junit.annotation.IgnoreEnvironment;

public class EnvironmentStatement extends Statement {

	private Description description;
	private Statement statement;

	public EnvironmentStatement(Statement statement, Description description) {
		this.statement = statement;
		this.description = description;
	}

	protected void before() {
		GivenEnvironment givenEnvironment = getGivenEnvironmentAnnotation();
		GivenEnvironments givenEnvironments = getAnnotation(GivenEnvironments.class);

		if ((givenEnvironment == null && givenEnvironments == null) || getIgnoreEnvironmentAnnotation() != null) return;

		if (givenEnvironment != null && givenEnvironments != null) {
			throw new EnvironmentException("@GivenEnvironments and @GivenEnvironment annotation in the same class or method !?");
		} else if (givenEnvironment != null) {
			EnvironmentExecutor.gimme().execute(givenEnvironment);
		} else if (givenEnvironments != null) {
			EnvironmentExecutor.gimme().execute(givenEnvironments);
		}
	}
	
	protected void after() {
		// Nothing to do here
	}
	
	private GivenEnvironment getGivenEnvironmentAnnotation() {
		return getAnnotation(GivenEnvironment.class);
	}

	private IgnoreEnvironment getIgnoreEnvironmentAnnotation() {
		return description.getAnnotation(IgnoreEnvironment.class);
	}

	private <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		T annotation = description.getAnnotation(annotationType);

		if (annotation != null) return annotation;

		return description.getTestClass().getAnnotation(annotationType);
	}

	@Override
	public void evaluate() throws Throwable {
		try {
			before();
			statement.evaluate();
		} catch (EnvironmentException e) {
			throw e;
		} catch (Exception e) {
			throw new EnvironmentException(e);
		} finally {
			after();
		}
	}

}
