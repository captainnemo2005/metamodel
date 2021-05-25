package analyticalModelImpl;

import analyticalModel.AnaModCarRoute;
import analyticalModel.AnaModNetwork;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Route;
import org.matsim.core.utils.collections.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnaModCarRouteImpl implements AnaModCarRoute {

    private final Logger logger                     =    Logger.getLogger(AnaModCarRouteImpl.class);
    private final Id<AnaModCarRoute> CarRouteId;
    private double RouteTravelTime                  =    0;
    private double RouteDistance                    =    0;
    private double RouteUtility                     =    0;

    private ArrayList<Id<Link>> RouteLinks          =    new ArrayList<>();
    private Map<Id<Link>, Double> LinkTravelTime    =    new HashMap<>();

    public AnaModCarRouteImpl(Route route){
        String [] part = route.getRouteDescription().split(" ");
        for(String linkId : part){
            RouteLinks.add(Id.createLinkId(linkId.trim()));
        }
        RouteDistance   =   route.getDistance();
        CarRouteId      =   Id.create(route.getRouteDescription(),AnaModCarRoute.class);
    }


    @Override
    public double getCarRouteTravelTime(AnaModNetwork network, Tuple<Double, Double> TimeBin,
                                        Map<String, Double> anaParams, Map<String, Double> Params) {
        return 0;
    }

    @Override
    public double getCarRouteUtility(AnaModNetwork network, Tuple<Double, Double> TimeBin,
                                     Map<String, Double> anaParams, Map<String, Double> Params) {
        return 0;
    }

    @Override
    public double getCarRouteDistance() {
        return RouteDistance;
    }

    @Override
    public String getCarRouteDetail() {
        return null;
    }

    @Override
    public double getOtherMoneyCost() {
        return 0;
    }

    @Override
    public Id<AnaModCarRoute> getCarRouteID() {
        return CarRouteId;
    }

    @Override
    public ArrayList<Id<Link>> getLinkIds() {
        return RouteLinks;
    }

    @Override
    public Map<Id<Link>, Double> getListOfLinkTravelTime() {
        return LinkTravelTime;
    }
    public double getRouteTravelTime() {
        return RouteTravelTime;
    }

    public void setRouteTravelTime(double routeTravelTime) {
        RouteTravelTime = routeTravelTime;
    }

    public double getRouteDistance() {
        return RouteDistance;
    }

    public void setRouteDistance(double routeDistance) {
        RouteDistance = routeDistance;
    }

    public double getRouteUtility() {
        return RouteUtility;
    }

    public void setRouteUtility(double routeUtility) {
        RouteUtility = routeUtility;
    }

    public ArrayList<Id<Link>> getRouteLinks() {
        return RouteLinks;
    }

    public void setRouteLinks(ArrayList<Id<Link>> routeLinks) {
        RouteLinks = routeLinks;
    }

    public Map<Id<Link>, Double> getLinkTravelTime() {
        return LinkTravelTime;
    }

    public void setLinkTravelTime(Map<Id<Link>, Double> linkTravelTime) {
        LinkTravelTime = linkTravelTime;
    }

}
