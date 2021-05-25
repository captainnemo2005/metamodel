package analyticalModel;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkFactory;
import org.matsim.api.core.v01.network.Node;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.pt.transitSchedule.api.TransitSchedule;
import org.matsim.utils.objectattributes.attributable.Attributes;

import java.util.Map;

public abstract class AnaModNetwork implements Network {

    protected Network network = ScenarioUtils.createScenario(ConfigUtils.createConfig()).getNetwork();

    abstract void addLinkTransitVolume(TransitSchedule ts);
    abstract void overLayTransitVehicle(TransitSchedule ts, Scenario sc);

    public static Node cloneNode(NetworkFactory networkFactory, Node node){
        return networkFactory.createNode(node.getId(), node.getCoord());
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    @Override
    public NetworkFactory getFactory() {
        return network.getFactory();
    }

    @Override
    public Map<Id<Node>, ? extends Node> getNodes() {
        return network.getNodes();
    }

    @Override
    public Map<Id<Link>, ? extends Link> getLinks() {
        return network.getLinks();
    }

    @Override
    public double getCapacityPeriod() {
        return network.getCapacityPeriod();
    }

    @Override
    public double getEffectiveLaneWidth() {
        return network.getEffectiveLaneWidth();
    }

    @Override
    public void addNode(Node nn) {
        network.addNode(nn);
    }

    @Override
    public void addLink(Link ll) {
        network.addLink(ll);
    }

    @Override
    public Node removeNode(Id<Node> nodeId) {
        return network.removeNode(nodeId);
    }

    @Override
    public Link removeLink(Id<Link> linkId) {
        return network.removeLink(linkId);
    }

    @Override
    public void setCapacityPeriod(double capPeriod) {
        network.setCapacityPeriod(capPeriod);
    }

    @Override
    public void setEffectiveCellSize(double effectiveCellSize) {
        network.setEffectiveCellSize(effectiveCellSize);
    }

    @Override
    public void setEffectiveLaneWidth(double effectiveLaneWidth) {
        network.setEffectiveLaneWidth(effectiveLaneWidth);
    }

    @Override
    public void setName(String name) {
        network.setName(name);
    }

    @Override
    public String getName() {
        return network.getName();
    }

    @Override
    public double getEffectiveCellSize() {
        return network.getEffectiveCellSize();
    }

    @Override
    public Attributes getAttributes() {
        return network.getAttributes();
    }
}
