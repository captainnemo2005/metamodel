package analyticalModel;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.collections.Tuple;

import java.util.ArrayList;
import java.util.Map;

public abstract class AnaModODPairs {
    private final Logger logger = Logger.getLogger(AnaModODPairs.class);
    protected final Network net;

    private Map<String, Tuple<Double,Double>> TimeBins;
    private Config config = ConfigUtils.createConfig();
    private Scenario sc;
    private Population pop;

    private Map<String, Map<Id<AnaModODPair>, Double>> odDemand;

    //This constructor is for testing only
    public AnaModODPairs(String popFileLocation, String netFileLocation,
                         Map<String, Tuple<Double,Double>> TimeBins){
        this.config.plans().setInputFile(popFileLocation);
        this.config.network().setInputFile(netFileLocation);
        sc              = ScenarioUtils.loadScenario(config);
        pop             = sc.getPopulation();
        net             = sc.getNetwork();
        this.TimeBins   = TimeBins;
    }
    public AnaModODPairs(Population pop, Network net, Scenario sc,
                         Map<String, Tuple<Double,Double>> TimeBins) {
        this.pop        = pop;
        this.net        = net;
        this.sc         = sc;
        this.TimeBins   = TimeBins;
    }

    public void GenerateODPairSet(){
        ArrayList<Trip> trips = new ArrayList<>();

        for(Id<Person> personId : pop.getPersons().keySet()){
            TripChain tripChain = getNewTripChain(pop.getPersons().get(personId).getSelectedPlan());
           // trips.addAll(tripChain.getTrips);
        }
    }

    protected abstract TripChain getNewTripChain(Plan SelectedPlan);
}
