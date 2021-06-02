package analyticalModel;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;

public abstract class AnaModTransitLink {

    private Id<Link> startLinkID;
    private Id<Link> endLinkID;
    private String startID;
    private String stopID;

    private double Passengers = 0;
    private double TravelTime = 0;

    public AnaModTransitLink(Id<Link> startLinkID, Id<Link> endLinkID, String startID, String stopID) {
        this.startLinkID = startLinkID;
        this.endLinkID = endLinkID;
        this.startID = startID;
        this.stopID = stopID;
    }

    abstract void addPassenger(double passenger, AnaModNetwork network);

    abstract Id<AnaModTransitLink> getTransitLinkId();


    public double getPassengers() {
        return Passengers;
    }

    public void setPassengers(double passengers) {
        Passengers = passengers;
    }

    public double getTravelTime() {
        return TravelTime;
    }

    public void setTravelTime(double travelTime) {
        TravelTime = travelTime;
    }


    public Id<Link> getStartLinkID() {
        return startLinkID;
    }

    public void setStartLinkID(Id<Link> startLinkID) {
        this.startLinkID = startLinkID;
    }

    public Id<Link> getEndLinkID() {
        return endLinkID;
    }

    public void setEndLinkID(Id<Link> endLinkID) {
        this.endLinkID = endLinkID;
    }

    public String getStartID() {
        return startID;
    }

    public void setStartID(String startID) {
        this.startID = startID;
    }

    public String getStopID() {
        return stopID;
    }

    public void setStopID(String stopID) {
        this.stopID = stopID;
    }
}
