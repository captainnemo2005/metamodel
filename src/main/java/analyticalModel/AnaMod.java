package analyticalModel;

import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Population;
import org.matsim.pt.transitSchedule.api.TransitSchedule;

public interface AnaMod {
    void GenRoutesAndOD(Population pop, Network net, TransitSchedule ts, Scenario sc);

    String MarginalUtilityofTravelCarName="MarginalUtilityofTravelCar";
    String MarginalUtilityofDistanceCarName="MarginalUtilityofDistanceCar";
    String MarginalUtilityofMoneyName="MarginalUtilityofMoney";
    String DistanceBasedMoneyCostCarName="DistanceBasedMoneyCostCar";
    String MarginalUtilityofTravelptName="MarginalUtilityofTravelpt";
    String MarginalUtilityOfDistancePtName="MarginalUtilityOfDistancePt";
    String MarginalUtilityofWaitingName="MarginalUtilityofWaiting";
    String UtilityOfLineSwitchName="UtilityOfLineSwitch";
    String MarginalUtilityOfWalkingName="MarginalUtilityOfWalking";
    String DistanceBasedMoneyCostWalkName="DistanceBasedMoneyCostWalk";
    String ModeConstantPtname="ModeConstantPt";
    String ModeConstantCarName="ModeConstantCar";
    String MarginalUtilityofPerformName="MarginalUtilityofPerform";
    String CapacityMultiplierName="CapacityMultiplier";

}
