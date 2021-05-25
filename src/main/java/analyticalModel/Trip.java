package analyticalModel;

import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.Node;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.network.NetworkUtils;

public class Trip {
    private final Logger logger = Logger.getLogger(Trip.class);

    Id<AnaModODPair> ODPairID;
    String PersonID;
    String mode;
    Coord act1Coord;
    Coord act2Coord;
    Node ONode;
    Node DNode;
    double tripStartTime;

    AnaModCarRoute carRoute;

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(Id<Person> personID) {
        PersonID = personID.toString();
    }
    public AnaModCarRoute getCarRoute() {
        return carRoute;
    }

    public void setCarRoute(AnaModCarRoute carRoute) {
        this.carRoute = carRoute;
    }

    public Id<AnaModODPair> genODPairID(Network net) {
        ONode = NetworkUtils.getNearestNode(net,act1Coord);
        DNode = NetworkUtils.getNearestNode(net,act2Coord);
        ODPairID = Id.create(ONode.getId().toString() + " " + DNode.getId().toString(),AnaModODPair.class);
        return ODPairID;
    }

    public Node getONode() {
        return ONode;
    }

    public void setONode(Node ONode) {
        this.ONode = ONode;
    }

    public double getTripStartTime() {
        return tripStartTime;
    }

    public void setTripStartTime(double tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public Node getDNode() {
        return DNode;
    }

    public void setDNode(Node DNode) {
        this.DNode = DNode;
    }
    public Coord getAct1() {
        return act1Coord;
    }

    public void setAct1(Coord act1) {
        this.act1Coord = act1;
    }

    public Coord getAct2() {
        return act2Coord;
    }

    public void setAct2(Coord act2) {
        this.act2Coord = act2;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
