package analyticalModel;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.utils.collections.Tuple;
import org.matsim.pt.transitSchedule.api.TransitLine;
import org.matsim.pt.transitSchedule.api.TransitRoute;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

import java.util.ArrayList;
import java.util.Map;

public abstract class AnaModDirectLink extends AnaModTransitLink{

    private Logger logger                   =           Logger.getLogger(AnaModDirectLink.class);
    private String LineID;
    private String RouteID;
    private ArrayList<Id<Link>> linksId     =            new ArrayList<>();
    private TransitSchedule ts;

    public AnaModDirectLink(Id<Link> startLinkID, Id<Link> endLinkID, String startID, String stopID,
                            String lineID, String routeID, TransitSchedule ts) {
        super(startID, stopID, startLinkID, endLinkID);
        logger.info("Running AnaModDirectLink");
        this.LineID = lineID;
        this.RouteID = routeID;
        this.ts = ts;
        Id<Link> startLink                      =       ts.getTransitLines().get(Id.create(this.LineID, TransitLine.class)).
                                                        getRoutes().get(Id.create(this.RouteID, TransitRoute.class)).
                                                        getRoute().getStartLinkId();
        Id<Link> endLink                        =       ts.getTransitLines().get(Id.create(this.LineID, TransitLine.class)).
                                                        getRoutes().get(Id.create(this.RouteID,TransitRoute.class)).
                                                        getRoute().getEndLinkId();

        ArrayList<Id<Link>> routeLinksId        =       new ArrayList<>();
        routeLinksId.add(startLink);
        routeLinksId.addAll(this.ts.getTransitLines().get(Id.create(this.LineID,TransitLine.class))
                                                     .getRoutes().get(Id.create(this.RouteID,TransitRoute.class))
                                                     .getRoute().getLinkIds());
        routeLinksId.add(endLink);

    }

    public abstract double CalculateTravelTime(AnaModNetwork network,
                                               Tuple<Double,Double> TimeBin,
                                               Map<String, Double> params,
                                               Map<String, Double> anaParams);
    public abstract double CalculatePhysicalDistance(Network network);


    public String getLineID() {
        return LineID;
    }

    public void setLineID(String lineID) {
        LineID = lineID;
    }

    public String getRouteID() {
        return RouteID;
    }

    public void setRouteID(String routeID) {
        RouteID = routeID;
    }

    public ArrayList<Id<Link>> getLinks() {
        return linksId;
    }

    public void setLinks(ArrayList<Id<Link>> links) {
        linksId = links;
    }

    public TransitSchedule getTs() {
        return ts;
    }

    public void setTs(TransitSchedule ts) {
        this.ts = ts;
    }
}
