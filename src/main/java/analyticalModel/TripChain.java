package analyticalModel;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.population.PopulationUtils;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

import java.util.ArrayList;
import java.util.List;

public abstract class TripChain {

    private final Logger logger = Logger.getLogger(TripChain.class);
    private Id<Person> PersonId;

    private ArrayList<Trip> trips           =       new ArrayList<>();
    private ArrayList<Activity> activities  =       new ArrayList<>();
    private PopulationFactory popFactory    =       PopulationUtils.getFactory();
    private ArrayList<Leg> legs             =       new ArrayList<>();
    public TripChain(Plan SelectedPlan,Scenario sc, TransitSchedule ts, Network net ){
        PersonId                        =    SelectedPlan.getPerson().getId();
        ArrayList<String> modes         =    new ArrayList<>();
        for(PlanElement pe: SelectedPlan.getPlanElements()){
            if(pe instanceof Activity){
                Activity activity = (Activity) pe;
                if(!activity.getType().equals("pt interaction")) {
                    activities.add(activity);
                }
            }else{
                Leg leg = (Leg) pe;
                if(modes.isEmpty() || !modes.contains(leg.getMode())){modes.add(leg.getMode());}
                legs.add(leg);
            }
        }
        for(int i = 0 ; i < activities.size()-1 ; i++){
            if((modes.contains("car")) && (modes.size() == 1)) {
                Trip trip = new Trip();
                trip.setAct1(activities.get(i).getCoord());
                trip.setAct2(activities.get(i+1).getCoord());
                trip.setTripStartTime(activities.get(i).getEndTime());
                trip.setMode("car");
                Route route = legs.get(i).getRoute();
                trip.setCarRoute(this.createCarRoute(route));
                trip.setPersonID(PersonId);
                trips.add(trip);
            }
        }
    }

    public ArrayList<Trip> getTrips(){
        return new ArrayList<Trip>(this.trips);
    }
    public abstract AnaModCarRoute createCarRoute(Route route);
}
