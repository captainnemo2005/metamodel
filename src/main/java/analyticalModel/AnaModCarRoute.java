package analyticalModel;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.utils.collections.Tuple;

import java.util.ArrayList;
import java.util.Map;

public interface AnaModCarRoute {
    /**
     * Purely contains method - not stores any value here.
     *
     **/
    double getCarRouteTravelTime(AnaModNetwork network, Tuple<Double,Double> TimeBin,
                                 Map<String, Double> anaParams, Map<String, Double> Params);
    double getCarRouteDistance();
    double getCarRouteUtility(AnaModNetwork network, Tuple<Double,Double> TimeBin,
                              Map<String, Double> anaParams, Map<String, Double> Params);
    String getCarRouteDetail();
    double getOtherMoneyCost();
    Id<AnaModCarRoute> getCarRouteID();
    ArrayList<Id<Link>> getLinkIds();
    Map<Id<Link>,Double> getListOfLinkTravelTime();

    static String getTimeBinID(double time, Map<String,Tuple<Double,Double>> TimeBins){
        if(time>24*3600) time = time - 24*3600;
        if(time == 0) time = 1;
        for(Map.Entry<String, Tuple<Double,Double>> e :TimeBins.entrySet()){
            if(time > e.getValue().getFirst() && time <= e.getValue().getSecond()){
                return e.getKey();
            }
        }
        return null;
    }

}
