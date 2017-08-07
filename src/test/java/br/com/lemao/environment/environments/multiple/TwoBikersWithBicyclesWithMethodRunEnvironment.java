package br.com.lemao.environment.environments.multiple;

import br.com.lemao.environment.annotation.Environment;
import br.com.lemao.environment.annotation.GivenEnvironment;
import br.com.lemao.environment.annotation.GivenEnvironments;

@Environment
public class TwoBikersWithBicyclesWithMethodRunEnvironment {

    @GivenEnvironments(environments = {
        @GivenEnvironment(TwoBicyclesEnvironment.class),
        @GivenEnvironment(OneMaleBikerWithBicycleEnvironment.class),
        @GivenEnvironment(OneFemaleBikerWithBicycleEnvironment.class)
    })
    public void run() { }

}
