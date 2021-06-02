import analyticalModel.Trip;
import analyticalModelImpl.AnaModTripChainImpl;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

import java.util.ArrayList;

public class Testing {

    public static void main(String[] args) {
        Config config = ConfigUtils.createConfig();
        String inputFolderLocation = "/Users/cptnemo2005/Downloads/RunDOTCOVID_2016_02_Outputs/MATSim/Outputs/";
        config.network().setInputFile(inputFolderLocation+"output_network.xml.gz");
        config.plans().setInputFile(inputFolderLocation + "output_plans.xml.gz");
        config.transit().setTransitScheduleFile(inputFolderLocation+"output_transitSchedule.xml.gz");
        config.transit().setVehiclesFile(inputFolderLocation+"output_transitVehicles.xml.gz");

        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles);
        config.controler().setLastIteration(0);
        Scenario sc   = ScenarioUtils.loadScenario(config);

        Population pop = sc.getPopulation();
        TransitSchedule ts = sc.getTransitSchedule();
        Network net = sc.getNetwork();

        for(Person person: pop.getPersons().values()){
            AnaModTripChainImpl  tripChain = new AnaModTripChainImpl(person.getSelectedPlan(),sc,ts,net);

        }

    }


}
