package br.com.lemao.environment.junit;

import java.lang.annotation.Annotation;

import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.exception.EnvironmentException;
import br.com.lemao.environment.executor.EnvironmentExecutor;
import br.com.lemao.environment.factory.EnvironmentFactory;
import br.com.lemao.environment.factory.EnvironmentReflectionFactory;
import br.com.lemao.environment.junit.annotation.IgnoreEnvironment;

public class EnvironmentStatement extends Statement {

	private Description description;
	private Statement statement;
	private EnvironmentFactory environmentFactory;

	public EnvironmentStatement(Statement statement, Description description) {
		this(statement, description, new EnvironmentReflectionFactory());
	}

	public EnvironmentStatement(Statement statement, Description description, EnvironmentFactory environmentFactory) {
		this.statement = statement;
		this.description = description;
		this.environmentFactory = environmentFactory;
	}

	protected void before() {
		validateGivenEnvironmentAndGivenEnvironmentsInTheSameClassOrMethod();

		if ((getGivenEnvironmentAnnotation() == null && getGivenEnvironmentsAnnotation() == null)
				|| getIgnoreEnvironmentAnnotation() != null)
			return;

		if (hasAnnotationMethod()) {
			GivenEnvironment givenEnvironmentMethod = getAnnotationMethod(GivenEnvironment.class);
			GivenEnvironments givenEnvironmentsMethod = getAnnotationMethod(GivenEnvironments.class);
			if (givenEnvironmentMethod != null) {
				EnvironmentExecutor.gimme(environmentFactory).execute(givenEnvironmentMethod);
			} else if (givenEnvironmentsMethod != null) {
				EnvironmentExecutor.gimme(environmentFactory).execute(givenEnvironmentsMethod);
			}
		} else {
			GivenEnvironment givenEnvironmentClass = getAnnotationClass(GivenEnvironment.class);
			GivenEnvironments givenEnvironmentsClass = getAnnotationClass(GivenEnvironments.class);
			if (givenEnvironmentClass != null) {
				EnvironmentExecutor.gimme(environmentFactory).execute(givenEnvironmentClass);
			} else if (givenEnvironmentsClass != null) {
				EnvironmentExecutor.gimme(environmentFactory).execute(givenEnvironmentsClass);
			}
		}
	}

	protected void after() {
		// Nothing to do here
	}

	private void validateGivenEnvironmentAndGivenEnvironmentsInTheSameClassOrMethod() {
		if ((getAnnotationMethod(GivenEnvironment.class) != null && getAnnotationMethod(GivenEnvironments.class) != null) ||
				(getAnnotationClass(GivenEnvironment.class) != null && getAnnotationClass(GivenEnvironments.class) != null)) {
			throw new EnvironmentException("@GivenEnvironments and @GivenEnvironment annotation in the same class or method !?");
		}
	}

	private boolean hasAnnotationMethod() {
		return getAnnotationMethod(GivenEnvironment.class) != null || getAnnotationMethod(GivenEnvironments.class) != null;
	}
	
	private GivenEnvironments getGivenEnvironmentsAnnotation() {
		return getAnnotation(GivenEnvironments.class);
	}
	
	private GivenEnvironment getGivenEnvironmentAnnotation() {
		return getAnnotation(GivenEnvironment.class);
	}

	private IgnoreEnvironment getIgnoreEnvironmentAnnotation() {
		return description.getAnnotation(IgnoreEnvironment.class);
	}

	private <T extends Annotation> T getAnnotation(Class<T> annotationType) {
		T annotation = getAnnotationMethod(annotationType);

		if (annotation != null)
			return annotation;

		return getAnnotationClass(annotationType);
	}

	private <T extends Annotation> T getAnnotationClass(Class<T> annotationType) {
		return description.getTestClass().getAnnotation(annotationType);
	}

	private <T extends Annotation> T getAnnotationMethod(Class<T> annotationType) {
		return description.getAnnotation(annotationType);
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
