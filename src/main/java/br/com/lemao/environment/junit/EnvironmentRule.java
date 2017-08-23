package br.com.lemao.environment.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import br.com.lemao.environment.factory.EnvironmentReflectionFactory;

public class EnvironmentRule implements TestRule {

	public Statement apply(Statement statement, Description description) {
		return new EnvironmentStatement(statement, description, new EnvironmentReflectionFactory());
	}

}
