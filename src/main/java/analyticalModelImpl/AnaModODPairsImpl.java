package analyticalModelImpl;

import analyticalModel.AnaModODPairs;
import analyticalModel.TripChain;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.utils.collections.Tuple;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

import java.util.Map;

public class AnaModODPairsImpl extends AnaModODPairs {

    private final Scenario sc;
    private final TransitSchedule ts;

    public AnaModODPairsImpl(Population pop, Network net, TransitSchedule ts,
                             Scenario sc, Map<String, Tuple<Double,Double>> TimeBins) {
        super(pop,net,sc,TimeBins);
        this.ts = ts;
        this.sc = sc;
    }

    @Override
    protected TripChain getNewTripChain(Plan SelectedPlan) {
        return new AnaModTripChainImpl(SelectedPlan,sc,ts,net);
    }
}
