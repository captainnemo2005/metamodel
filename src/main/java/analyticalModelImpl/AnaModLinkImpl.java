package analyticalModelImpl;

import analyticalModel.AnaModLink;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.utils.collections.Tuple;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AnaModLinkImpl extends AnaModLink {
    private final Logger logger = Logger.getLogger(AnaModLinkImpl.class);

    private ConcurrentHashMap<String,Double> transitMapping = new ConcurrentHashMap<>();
    private double alpha = 0.15;
    private double beta = 4;

    public AnaModLinkImpl(Link link){
        super(link);
    }

    public double calLinkTravelTime(Tuple<Double,Double> TimeBin,
                                    Map<String,Double> anaParams, Map<String, Double> Params){
       double tt;
        if(!this.link.getAllowedModes().contains("train")){
            double totalPCU = super.getCarVolume() + super.getTransitVolume();
            double capacity = super.getCapacity()*(TimeBin.getSecond() - TimeBin.getFirst())/3600*Params.get(AnaModImpl.CapacityMultiplierName);
            double FreeFlowTravelTime = this.getLength()/this.getFreespeed();
            tt = FreeFlowTravelTime*(1+0.15*Math.pow(totalPCU/capacity,beta));
            if(tt > 2*3600 || Double.isNaN(tt))
                logger.warn("Error value of travel time on PC link " + link.getId());
            return tt;
        }else{
            tt = this.link.getLength()/(this.link.getFreespeed()*1000/3600);
            if(Double.isNaN(tt))
                logger.warn("Error value of travel time on PT link "+ link.getId());
            return tt;
        }
    }

    public ConcurrentHashMap<String, Double> getTransitMapping() {
        return transitMapping;
    }

    public void setTransitMapping(ConcurrentHashMap<String, Double> transitMapping) {
        this.transitMapping = transitMapping;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }
}
