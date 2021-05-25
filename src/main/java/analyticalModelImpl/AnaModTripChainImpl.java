package analyticalModelImpl;

import analyticalModel.AnaModCarRoute;
import analyticalModel.TripChain;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Route;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

public class AnaModTripChainImpl extends TripChain {
    public AnaModTripChainImpl(Plan SelectedPlan, Scenario sc, TransitSchedule ts, Network net) {
        super(SelectedPlan,sc,ts,net);
    }

    @Override
    public AnaModCarRoute createCarRoute(Route route) {
        return new AnaModCarRouteImpl(route);
    }
}
