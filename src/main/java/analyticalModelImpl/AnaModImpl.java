package analyticalModelImpl;

import analyticalModel.AnaMod;
import analyticalModel.AnaModODPair;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.utils.collections.Tuple;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AnaModImpl implements AnaMod {
    private final Logger logger = Logger.getLogger(AnaModImpl.class);

    private Map<String, Tuple<Double,Double>> TimeBins;

    private Map<String, HashMap<Id<AnaModODPair>,Double>> Demand          = new ConcurrentHashMap<>();
    private Map<String,HashMap<Id<AnaModODPair>,Double>> carDemand        = new ConcurrentHashMap<>();
    private AnaModODPairsImpl odPairs;

    private Map<String, Map<Id<AnaModODPair>, Double>> demandTotal        = new ConcurrentHashMap<>();
    private Map<String, Map<Id<AnaModODPair>, Double>> demandCar          = new ConcurrentHashMap<>();

    private Map<String, Double> CalibrationParams                           = new HashMap<>();
    private Map<String, Double> AnalyticalModelInternalParams               = new HashMap<>();
    private Map<String, Tuple <Double,Double>> AnalyticalModelParamsLimit   = new HashMap<>();

    private Map<String, ArrayList<Double>> beta                             = new ConcurrentHashMap<>();
    private Map<String,ArrayList<Double>> error                             = new ConcurrentHashMap<>();
    private Map<String,ArrayList<Double>> error1                            = new ConcurrentHashMap<>();

    protected Map<String,Map<Id<Link>,Double>> outputCarLinkTT              = new ConcurrentHashMap<>();

    public static final String BPRalphaName="BPRalpha";
    public static final String BPRbetaName="BPRbeta";
    public static final String LinkMiuName="LinkMiu";
    public static final String ModeMiuName="ModeMiu";
    public static final String TransferalphaName="Transferalpha";
    public static final String TransferbetaName="Transferbeta";

    public AnaModImpl(Map<String, Tuple<Double, Double>> timeBins) {
        TimeBins = timeBins;
        defaultParameterInitiation(null);
        for(String timeBin : timeBins.keySet()){
            demandTotal.put(timeBin, new HashMap<Id<AnaModODPair>, Double>());
            carDemand.put(timeBin, new HashMap<Id<AnaModODPair>, Double>());

            beta.put(timeBin, new ArrayList<>());
            error.put(timeBin, new ArrayList<>());
            error1.put(timeBin, new ArrayList<>());

            outputCarLinkTT.put(timeBin,new HashMap<>());
        }
        logger.info("Creating analytical model completed~!");
    }

    @Override
    public void GenRoutesAndOD(Population pop, Network net, TransitSchedule ts, Scenario sc) {
        this.setODPairs(new AnaModODPairsImpl(pop,net,ts,sc, TimeBins));
       // this.getOdPairs().generateODPairSet();
       // this.getOdPairs().generateRouteAndLinkIncidence(0.);
    }
    public void setODPairs(AnaModODPairsImpl odPairs){
        this.odPairs = odPairs;
    }
    private void defaultParameterInitiation(Config config){
        this.AnalyticalModelInternalParams.put(AnaModImpl.LinkMiuName, 0.008);
        this.AnalyticalModelInternalParams.put(AnaModImpl.ModeMiuName, 0.01);
        this.AnalyticalModelInternalParams.put(AnaModImpl.BPRalphaName, 0.15);
        this.AnalyticalModelInternalParams.put(AnaModImpl.BPRbetaName, 4.);
        this.AnalyticalModelInternalParams.put(AnaModImpl.TransferalphaName, 0.5);
        this.AnalyticalModelInternalParams.put(AnaModImpl.TransferbetaName, 1.);
        this.loadAnalyticalModelInternalParamsLimit();
        if(config==null) {
            config= ConfigUtils.createConfig();
        }
        this.CalibrationParams.put(AnaModImpl.MarginalUtilityofTravelCarName,config.planCalcScore().getOrCreateModeParams("car").getMarginalUtilityOfTraveling());
        this.CalibrationParams.put(AnaModImpl.MarginalUtilityofDistanceCarName,config.planCalcScore().getOrCreateModeParams("car").getMarginalUtilityOfDistance());
        this.CalibrationParams.put(AnaModImpl.MarginalUtilityofMoneyName,config.planCalcScore().getMarginalUtilityOfMoney());
        this.CalibrationParams.put(AnaModImpl.DistanceBasedMoneyCostCarName,config.planCalcScore().getOrCreateModeParams("car").getMonetaryDistanceRate());
        this.CalibrationParams.put(AnaModImpl.MarginalUtilityofTravelptName, config.planCalcScore().getOrCreateModeParams("pt").getMarginalUtilityOfTraveling());
        this.CalibrationParams.put(AnaModImpl.MarginalUtilityOfDistancePtName, config.planCalcScore().getOrCreateModeParams("pt").getMarginalUtilityOfDistance());
        this.CalibrationParams.put(AnaModImpl.MarginalUtilityofWaitingName,config.planCalcScore().getMarginalUtlOfWaitingPt_utils_hr());
        this.CalibrationParams.put(AnaModImpl.UtilityOfLineSwitchName,config.planCalcScore().getUtilityOfLineSwitch());
        this.CalibrationParams.put(AnaModImpl.MarginalUtilityOfWalkingName, config.planCalcScore().getOrCreateModeParams("walk").getMarginalUtilityOfTraveling());
        this.CalibrationParams.put(AnaModImpl.DistanceBasedMoneyCostWalkName, config.planCalcScore().getOrCreateModeParams("walk").getMonetaryDistanceRate());
        this.CalibrationParams.put(AnaModImpl.ModeConstantPtname,config.planCalcScore().getOrCreateModeParams("pt").getConstant());
        this.CalibrationParams.put(AnaModImpl.ModeConstantCarName,config.planCalcScore().getOrCreateModeParams("car").getConstant());
        this.CalibrationParams.put(AnaModImpl.MarginalUtilityofPerformName, config.planCalcScore().getPerforming_utils_hr());
        this.CalibrationParams.put(AnaModImpl.CapacityMultiplierName, 1.0);
    }
    protected void loadAnalyticalModelInternalParamsLimit() {
        this.AnalyticalModelParamsLimit.put(AnaModImpl.LinkMiuName, new Tuple<Double,Double>(0.0075,0.25));
        this.AnalyticalModelParamsLimit.put(AnaModImpl.ModeMiuName, new Tuple<Double,Double>(0.01,0.5));
        this.AnalyticalModelParamsLimit.put(AnaModImpl.BPRalphaName, new Tuple<Double,Double>(0.10,4.));
        this.AnalyticalModelParamsLimit.put(AnaModImpl.BPRbetaName, new Tuple<Double,Double>(1.,15.));
        this.AnalyticalModelParamsLimit.put(AnaModImpl.TransferalphaName, new Tuple<Double,Double>(0.25,5.));
        this.AnalyticalModelParamsLimit.put(AnaModImpl.TransferbetaName, new Tuple<Double,Double>(0.75,4.));
    }
}
