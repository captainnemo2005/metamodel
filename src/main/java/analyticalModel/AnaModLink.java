package analyticalModel;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Node;
import org.matsim.utils.objectattributes.attributable.Attributes;

import java.util.Set;

public class AnaModLink implements Link {

    protected Link link;
    protected double CarVolume = 0;
    protected double TransitVolume = 0;
    protected double PassengerVolume = 0;
    protected double LinkTravelTime = 0;

    public AnaModLink(Link link){
        this.link = link;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public double getCarVolume() {
        return CarVolume;
    }

    public void setCarVolume(double carVolume) {
        CarVolume = carVolume;
    }

    public double getTransitVolume() {
        return TransitVolume;
    }

    public void setTransitVolume(double transitVolume) {
        TransitVolume = transitVolume;
    }

    public double getPassengerVolume() {
        return PassengerVolume;
    }

    public void setPassengerVolume(double passengerVolume) {
        PassengerVolume = passengerVolume;
    }

    public double getLinkTravelTime() {
        return LinkTravelTime;
    }

    public void setLinkTravelTime(double linkTravelTime) {
        LinkTravelTime = linkTravelTime;
    }

    @Override
    public boolean setFromNode(Node node) {
        return link.setFromNode(node);
    }

    @Override
    public boolean setToNode(Node node) {
        return link.setToNode(node);
    }

    @Override
    public Node getToNode() {
        return link.getToNode();
    }

    @Override
    public Node getFromNode() {
        return link.getFromNode();
    }

    @Override
    public double getLength() {
        return link.getLength();
    }

    @Override
    public double getNumberOfLanes() {
        return link.getNumberOfLanes();
    }

    @Override
    public double getNumberOfLanes(double time) {
        return link.getNumberOfLanes(time);
    }

    @Override
    public double getFreespeed() {
        return link.getFreespeed();
    }

    @Override
    public double getFreespeed(double time) {
        return link.getFreespeed(time);
    }

    @Override
    public double getCapacity() {
        return link.getCapacity();
    }

    @Override
    public double getCapacity(double time) {
        return link.getFreespeed(time);
    }

    @Override
    public void setFreespeed(double freespeed) {
        link.setFreespeed(freespeed);
    }

    @Override
    public void setLength(double length) {
        link.setLength(length);
    }

    @Override
    public void setNumberOfLanes(double lanes) {
        link.setNumberOfLanes(lanes);
    }

    @Override
    public void setCapacity(double capacity) {
        link.setCapacity(capacity);
    }

    @Override
    public void setAllowedModes(Set<String> modes) {
        link.setAllowedModes(modes);
    }

    @Override
    public Set<String> getAllowedModes() {
        return link.getAllowedModes();
    }

    @Override
    public double getFlowCapacityPerSec() {
        return link.getFlowCapacityPerSec();
    }

    @Override
    public double getFlowCapacityPerSec(double time) {
        return link.getFlowCapacityPerSec(time);
    }

    @Override
    public Coord getCoord() {
        return link.getCoord();
    }

    @Override
    public Id<Link> getId() {
        return link.getId();
    }

    @Override
    public Attributes getAttributes() {
        return link.getAttributes();
    }
}
