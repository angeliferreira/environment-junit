package br.com.lemao.environment.test;

import static br.com.lemao.environment.environments.AbstractBikerBicycleEnvironment.oneBicycle;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;
import br.com.lemao.environment.environments.BikerEnvironment;
import br.com.lemao.environment.environments.OneMaleBikerAndOneBicycleForThisBiker;
import br.com.lemao.environment.environments.TwoBikersOneMaleAnotherFemaleAndOneBicycleForMaleBiker;
import br.com.lemao.environment.environments.multiple.OneFemaleBikerWithBicycleEnvironment;
import br.com.lemao.environment.environments.multiple.OneMaleBikerWithBicycleEnvironment;
import br.com.lemao.environment.environments.multiple.TwoBicyclesEnvironment;
import br.com.lemao.environment.junit.InMemoryRule;
import br.com.lemao.environment.junit.annotation.IgnoreEnvironment;
import br.com.lemao.environment.model.bicycle.Bicycle;
import br.com.lemao.environment.model.bicycle.support.BicycleInMemorySupport;
import br.com.lemao.environment.model.biker.Biker;
import br.com.lemao.environment.model.biker.support.BikerInMemorySupport;
import br.com.lemao.environment.model.gender.Gender;

@GivenEnvironment(OneMaleBikerAndOneBicycleForThisBiker.class)
public class EnvironmentWithClassAnnotationTest {

	@Rule
	public InMemoryRule myRule = new InMemoryRule();

	@Test
	public void oneBikerAndOneBicycleForThisBikerCreatedByEnvironment() {
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(1));

		Biker biker = bikers.get(0);
		assertThat(biker.getGender(), is(Gender.MALE));
		assertThat(biker.getName(), is("Lemão"));

		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(1));

		Bicycle bicycle = bicycles.get(0);
		assertThat(bicycle.getModelName(), is("S-WORKS EPIC 29"));
		assertThat(bicycle.getSerialNumber(), is(165487L));
		assertThat(bicycle.getOwner(), is(biker));
	}

	@Test
	@IgnoreEnvironment
	public void nothingCreatedBecauseTheEnvironmentWasIgnored() {
		assertTrue(BikerInMemorySupport.findAll().isEmpty());
		assertTrue(BicycleInMemorySupport.findAll().isEmpty());
	}

	@Test
	@GivenEnvironment(TwoBikersOneMaleAnotherFemaleAndOneBicycleForMaleBiker.class)
	public void twoBikersOneMaleAnotherFemaleAndOneBicycleForMaleBikerCreatedByEnvironment() {
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));

		Biker lemaoBiker = BikerInMemorySupport.findByName("Lemão");
		assertThat(lemaoBiker.getGender(), is(Gender.MALE));
		assertThat(lemaoBiker.getName(), is("Lemão"));

		Biker oliviaBiker = BikerInMemorySupport.findByName("Olivia");
		assertThat(oliviaBiker.getGender(), is(Gender.FEMALE));
		assertThat(oliviaBiker.getName(), is("Olivia"));

		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(1));

		Bicycle bicycle = bicycles.get(0);
		assertThat(bicycle.getModelName(), is("S-WORKS EPIC 29"));
		assertThat(bicycle.getSerialNumber(), is(165487L));
		assertThat(bicycle.getOwner(), is(lemaoBiker));
	}

	@Test
	@GivenEnvironment(value = BikerEnvironment.class, environmentName = "twoBikersOneMaleAnotherFemale")
	public void twoBikersOneMaleAnotherFemaleCreatedByEnvironment() {
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));

		Biker zeBiker = BikerInMemorySupport.findByName("Zé Grandão");
		assertThat(zeBiker.getGender(), is(Gender.MALE));
		assertThat(zeBiker.getName(), is("Zé Grandão"));

		Biker maricotinhaBiker = BikerInMemorySupport.findByName("Maria Maricotinha");
		assertThat(maricotinhaBiker.getGender(), is(Gender.FEMALE));
		assertThat(maricotinhaBiker.getName(), is("Maria Maricotinha"));
	}

	@Test
	@GivenEnvironment(value = BikerEnvironment.class, environmentName = "twoBikersOneMaleAnotherFemaleWithBicycles")
	public void twoBikersOneMaleAnotherFemaleWithBicyclesCreatedByEnvironment() {
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));

		Biker zeBiker = BikerInMemorySupport.findByName("Zé Grandão");
		assertThat(zeBiker.getGender(), is(Gender.MALE));
		assertThat(zeBiker.getName(), is("Zé Grandão"));

		Biker maricotinhaBiker = BikerInMemorySupport.findByName("Maria Maricotinha");
		assertThat(maricotinhaBiker.getGender(), is(Gender.FEMALE));
		assertThat(maricotinhaBiker.getName(), is("Maria Maricotinha"));

		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(2));

		Bicycle epicBicycle = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29");
		assertThat(epicBicycle.getModelName(), is("S-WORKS EPIC 29"));
		assertThat(epicBicycle.getSerialNumber(), is(165487L));
		assertThat(epicBicycle.getOwner(), is(zeBiker));

		Bicycle allezBicycle = BicycleInMemorySupport.findByModelName("S-WORKS ALLEZ DI2");
		assertThat(allezBicycle.getModelName(), is("S-WORKS ALLEZ DI2"));
		assertThat(allezBicycle.getSerialNumber(), is(98657L));
		assertThat(allezBicycle.getOwner(), is(maricotinhaBiker));
	}

	@Test
	@GivenEnvironment(value = BikerEnvironment.class, environmentName = "twoBikersOneMaleAnotherFemale")
	public void twoBikersOneMaleAnotherFemaleWithBicyclesCreatedByTest() {
		List<Biker> bikers = BikerInMemorySupport.findAll();
		assertThat(bikers.size(), is(2));

		Biker zeBiker = BikerInMemorySupport.findByName("Zé Grandão");
		assertThat(zeBiker.getGender(), is(Gender.MALE));
		assertThat(zeBiker.getName(), is("Zé Grandão"));

		Biker maricotinhaBiker = BikerInMemorySupport.findByName("Maria Maricotinha");
		assertThat(maricotinhaBiker.getGender(), is(Gender.FEMALE));
		assertThat(maricotinhaBiker.getName(), is("Maria Maricotinha"));

		assertTrue(BicycleInMemorySupport.findAll().isEmpty());

		Bicycle epicBicycle = oneBicycle().forBiker(zeBiker).withModelName("S-WORKS EPIC 29").withSerialNumber(165487L)
				.gimme();
		BicycleInMemorySupport.persist(epicBicycle);

		Bicycle allezBicycle = oneBicycle().forBiker(maricotinhaBiker).withModelName("S-WORKS ALLEZ DI2")
				.withSerialNumber(98657L).gimme();
		BicycleInMemorySupport.persist(allezBicycle);

		List<Bicycle> bicycles = BicycleInMemorySupport.findAll();
		assertThat(bicycles.size(), is(2));

		epicBicycle = BicycleInMemorySupport.findByModelName("S-WORKS EPIC 29");
		assertThat(epicBicycle.getModelName(), is("S-WORKS EPIC 29"));
		assertThat(epicBicycle.getSerialNumber(), is(165487L));
		assertThat(epicBicycle.getOwner(), is(zeBiker));

		allezBicycle = BicycleInMemorySupport.findByModelName("S-WORKS ALLEZ DI2");
		assertThat(allezBicycle.getModelName(), is("S-WORKS ALLEZ DI2"));
		assertThat(allezBicycle.getSerialNumber(), is(98657L));
		assertThat(allezBicycle.getOwner(), is(maricotinhaBiker));
	}

	@Test
	@GivenEnvironments(environments = { @GivenEnvironment(TwoBicyclesEnvironment.class),
			@GivenEnvironment(OneMaleBikerWithBicycleEnvironment.class),
			@GivenEnvironment(OneFemaleBikerWithBicycleEnvironment.class) })
	public void twoBikersWithBicyclesCreatedByTestMethodMultipleEnvironments() {
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
