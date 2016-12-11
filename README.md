<p align="center">
  <img src="https://github.com/angeliferreira/environment-junit/blob/master/environment-junit.jpg?raw=true"/>
</p>

# environment-junit [![Build Status](https://travis-ci.org/angeliferreira/environment-junit.png?branch=master)](https://travis-ci.org/angeliferreira/environment-junit)

Environment JUnit is a basic project for running set ups before running each test method in a customized way than using @org.junit.Before and @org.junit.After of JUnit.


## Maven integration

To integrate *Environment JUnit* to your Maven project, you must declare the following dependency (Not in maven repository yet, must install it local):

```xml
<dependency>
    <groupId>br.com.lemao</groupId>
    <artifactId>environment-junit</artifactId>
    <version>1.0</version>
    <scope>test</scope>
</dependency>
```

## Getting Started

* *_[Basic Structure](https://github.com/angeliferreira/environment#basic-structure)_*

### @IgnoreEnvironment annotation

The *IgnoreEnvironment* annotation tells the test runner that it should ignore the Environment annotated in the implementing class.

Its use in a test case would be as follows:

```java
@GivenEnvironment(EnvironmentSample.class)
public class Sample {

   @Test
   @IgnoreEnvironment
   public void method() {
      org.junit.Assert.assertTrue(SampleUtil.findAll().isEmpty());
   }

}
```

## Custom structure

It is often necessary to do some settings before running each Environment. Now what?

No problem, you can customize a execution before each environment and after the test execution. But how can I do this customization?

It is only necessary to create a *Rule* and a *Statement* extending *EnvironmentStatement*.

```java
public class MyRule implements TestRule {

   @Override
   public Statement apply(Statement base, Description description) {
      return new MyStatement(base, description);
   }

}
```

```java
public class MyStatement extends EnvironmentStatement {

   public MyStatement(Statement statement, Description description) {
      super(statement, description);
   }

   @Override
   protected void before() {
      // I call what I want to do before running the Environment here!
      super.before(); // This call executes the environment
   }

   @Override
   protected void after() {
      // I call what I want to do after running the Test here!
   }

}
```

Its use in a test case would be as follows:

```java
public class Sample {

   @Rule
   public MyRule myRule = new MyRule();

   @Test
   @GivenEnvironment(SampleEnvironment.class)
   public void sampleTest() {
      org.junit.Assert.assertTrue(SampleUtil.findAll().isEmpty());
   }

}
```

In this case the execution flow is as follows:

```java
br.com.lemao.environment.MyStatement.before()
br.com.lemao.environment.SampleEnvironment.run()
br.com.lemao.environment.Sample.sampleTest()
br.com.lemao.environment.MyStatement.after()
```

It's also available one JUnit runner, this runner provide the same features that the default JUnit runner provide plus the 
environment executions.   

```java
@RunWith(EnvironmentRunner.class)
public class Sample {

   @Test
   @GivenEnvironment(SampleEnvironment.class)
   public void sampleTest() {
      org.junit.Assert.assertTrue(SampleUtil.findAll().isEmpty());
   }
}
```

The runner can have their behavior easily customized by overriding the method *getTestRules()*, doing it, you can add additional
*TestRule* to perform any expected behavior after the default implementation.

## You must also see

* *_[Environment](https://github.com/angeliferreira/environment)_*
* *_[SimulaTest - Simulatest Test Harness Framework](https://github.com/gabrielsuch/simulatest)_*
* *_[Fixture Factory](https://github.com/six2six/fixture-factory)_*
* *_[DbUnit](http://dbunit.sourceforge.net/)_*
