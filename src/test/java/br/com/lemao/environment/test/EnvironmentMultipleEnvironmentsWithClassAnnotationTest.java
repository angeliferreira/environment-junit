package br.com.lemao.environment.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.environments.multiple.OneBikerWithBicycleWithMethodRunAndGivenEnvironmentsEnvironment;
import br.com.lemao.environment.environments.multiple.OneFemaleBikerWithBicycleEnvironment;
import br.com.lemao.environment.environments.multiple.OneMaleBikerWithBicycleEnvironment;
import br.com.lemao.environment.environments.multiple.TwoBicyclesEnvironment;
import br.com.lemao.environment.environments.multiple.TwoBikersWithBicyclesEnvironment;
import br.com.lemao.environment.environments.multiple.TwoBikersWithBicyclesWithMethodRunEnvironment;
import br.com.lemao.environment.junit.InMemoryRule;
import br.com.lemao.environment.junit.annotation.IgnoreEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;
import br.com.lemao.environment.model.gender.Gender;

@GivenEnvironments(environments = {
		@GivenEnvironment(TwoBicyclesEnvironment.class),
		@GivenEnvironment(OneMaleBikerWithBicycleEnvironment.class),
		@GivenEnvironment(OneFemaleBikerWithBicycleEnvironment.class)
})
public class EnvironmentMultipleEnvironmentsWithClassAnnotationTest {

	@Rule
	public InMemoryRule myRule = new InMemoryRule();

	@Test
	public void twoBikersWithBicyclesCreatedByTestClassMultipleEnvironments() {
		assertTwoBikersWithBicycles();
	}

	@Test
	@GivenEnvironment(TwoBikersWithBicyclesEnvironment.class)
    public void twoBikersWithBicyclesCreatedByTestMethodMultipleEnvironments() {
        assertTwoBikersWithBicycles();
    }

	@Test
	@GivenEnvironment(TwoBikersWithBicyclesWithMethodRunEnvironment.class)
	public void twoBikersWithBicyclesCreatedByTestMethodMultipleEnvironmentsWithMethodRun() {
	    assertTwoBikersWithBicycles();
	}

	@Test
	@IgnoreEnvironment
	public void nothingCreatedBecauseTheEnvironmentWasIgnored() {
		assertTrue(BikerInMemorySupport.findAll().isEmpty());
		assertTrue(BicycleInMemorySupport.findAll().isEmpty());
	}

	@Test
	@GivenEnvironments(environments = {
			@GivenEnvironment(TwoBicyclesEnvironment.class),
			@GivenEnvironment(OneMaleBikerWithBicycleEnvironment.class),
			@GivenEnvironment(OneFemaleBikerWithBicycleEnvironment.class)
	})
	public void twoBikersWithBicyclesCreatedByTestMethodMultipleEnvironmentsOnlyClassEnvironment() {
		assertTwoBikersWithBicycles();
	}

	@Test
	@GivenEnvironments(environments = {
			@GivenEnvironment(value=TwoBicyclesEnvironment.class, environmentName="run"),
			@GivenEnvironment(value=OneMaleBikerWithBicycleEnvironment.class, environmentName="run"),
			@GivenEnvironment(value=OneFemaleBikerWithBicycleEnvironment.class, environmentName="run")
	})
	public void twoBikersWithBicyclesCreatedByTestMethodMultipleEnvironmentsOnlyMethodEnvironment() {
		assertTwoBikersWithBicycles();
	}

	@Test
	@GivenEnvironments(environments = {
			@GivenEnvironment(TwoBicyclesEnvironment.class),
			@GivenEnvironment(value=OneMaleBikerWithBicycleEnvironment.class, environmentName="run"),
			@GivenEnvironment(value=OneFemaleBikerWithBicycleEnvironment.class, environmentName="run")
	})
	public void twoBikersWithBicyclesCreatedByTestMethodMultipleEnvironmentsWithMethodEnvironmentAndClassEnvironment() {
		assertTwoBikersWithBicycles();
	}

	@Test
    @GivenEnvironment(OneBikerWithBicycleWithMethodRunAndGivenEnvironmentsEnvironment.class)
    public void oneBikerWithBicycleCreatedByTestMethodMultipleEnvironmentsWithMethodRunAndGivenEnvironments() {
	    List<Biker> bikers = BikerInMemorySupport.findAll();
        assertThat(bikers.size(), is(1));

        Biker lemaoBiker = BikerInMemorySupport.findByName("Lemão");
        assertThat(lemaoBiker.getGender(), is(Gender.MALE));
        assertThat(lemaoBiker.getName(), is("Lemão"));

        List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
        assertThat(bicycles.size(), is(1));

        Bicycle bicycleBlue = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29 - BLACK");
        assertThat(bicycleBlue.getModelName(), is("S-WORKS EPIC 29 - BLACK"));
        assertThat(bicycleBlue.getSerialNumber(), is(99999L));
        assertThat(bicycleBlue.getOwner(), is(lemaoBiker));
    }

	private void assertTwoBikersWithBicycles() {
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));

		Biker lemaoBiker = BikerInMemorySupport.findByName("Lemão");
		assertThat(lemaoBiker.getGender(), is(Gender.MALE));
		assertThat(lemaoBiker.getName(), is("Lemão"));

		Biker mariaBiker = BikerInMemorySupport.findByName("Maria Maricotinha");
		assertThat(mariaBiker.getGender(), is(Gender.FEMALE));
		assertThat(mariaBiker.getName(), is("Maria Maricotinha"));

		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(2));

		Bicycle bicycleBlue = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29 - BLUE");
		assertThat(bicycleBlue.getModelName(), is("S-WORKS EPIC 29 - BLUE"));
		assertThat(bicycleBlue.getSerialNumber(), is(165487L));
		assertThat(bicycleBlue.getOwner(), is(lemaoBiker));

		Bicycle bicyclePink = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29 - PINK");
		assertThat(bicyclePink.getModelName(), is("S-WORKS EPIC 29 - PINK"));
		assertThat(bicyclePink.getSerialNumber(), is(132423L));
		assertThat(bicyclePink.getOwner(), is(mariaBiker));
	}

}
