package analyticalModel;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;

import java.util.Map;

public abstract class AnaModTransferLink extends AnaModTransitLink{

    private double WalkDistance         =       0;
    private double WaitTime             =       0;

    public AnaModTransferLink(String startStopId, String stopStopId,
                              Id<Link> startLinkId, Id<Link> stopLinkId){
        super(startStopId,stopStopId,startLinkId,stopLinkId);
    }

    public abstract double getWaitingTime(Map<String,Double> anaParams, AnaModNetwork Network);

    public double getWalkDistance() {
        return WalkDistance;
    }

    public void setWalkDistance(double walkDistance) {
        WalkDistance = walkDistance;
    }

    public double getWaitTime() {
        return WaitTime;
    }

    public void setWaitTime(double waitTime) {
        WaitTime = waitTime;
    }
}
