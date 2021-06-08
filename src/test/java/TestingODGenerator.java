import analyticalModel.AnaModCarRoute;
import analyticalModel.AnaModODPair;
import analyticalModel.AnaModODPairs;
import analyticalModel.Trip;
import analyticalModelImpl.AnaModODPairsImpl;
import analyticalModelImpl.AnaModTripChainImpl;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.collections.Tuple;
import org.matsim.core.utils.gis.ShapeFileReader;
import org.matsim.facilities.ActivityFacility;
import org.matsim.pt.transitSchedule.api.TransitSchedule;
import org.opengis.feature.simple.SimpleFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Testing {

    public static final String inputsLocation           =    "/Users/cptnemo2005/Desktop/Work/MetaModel/metamodel/data/inputs/";
    public static final String shapes                   =    "/Users/cptnemo2005/Desktop/Work/MetaModel/metamodel/data/shapefiles/";
    public static Map<Integer, Geometry> zones          =     new HashMap<>();
    public static Map<String, Tuple<Double,Double>> tb  =    new HashMap<>();

    public static void main(String[] args) throws ParseException {
        Config config = ConfigUtils.createConfig();
        config.network().setInputFile(inputsLocation+"output_network.xml.gz");
        config.plans().setInputFile(inputsLocation + "output_plans.xml.gz");
        config.transit().setTransitScheduleFile(inputsLocation+"output_transitSchedule.xml.gz");
        config.transit().setVehiclesFile(inputsLocation+"output_transitVehicles.xml");

        config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.overwriteExistingFiles);
        config.controler().setLastIteration(0);

        Scenario sc   = ScenarioUtils.loadScenario(config);
        Population pop = sc.getPopulation();
        TransitSchedule ts = sc.getTransitSchedule();
        Network net = sc.getNetwork();
        createZones();
        createTimeBins();

        AnaModODPairsImpl odPairs = new AnaModODPairsImpl(pop,net,ts,sc,tb,zones);
        System.out.println("Total number of OD pairs " + odPairs.getOdPairs().size());
        for(Id<AnaModODPair> id : odPairs.getOdPairs().keySet()){
            int TotalCarTrips = 0;
            Map<String, Double> odDemand = odPairs.getOdPairs().get(id).getOdDemand();
            System.out.println("For OD " + id.toString());
            System.out.println("Car Agents for this OD is = " + odPairs.getOdPairs().get(id).getCarAgents());
            System.out.println("Number of car routes = " +odPairs.getOdPairs().get(id).getCarRoutes().size() + " car routes detailed check " + odPairs.getOdPairs().get(id).getDetailedCarRoutes().size());
            if(odPairs.getOdPairs().get(id).getCarRoutes().size() > 1){
                for(int i = 0 ; i <  odPairs.getOdPairs().get(id).getCarRoutes().size() ; i++){
                    System.out.println(odPairs.getOdPairs().get(id).getCarRoutes().get(i).toString());
                }
            }
            for(String Slot : odDemand.keySet()){
                System.out.println("Demand at " + Slot  + " = "+odDemand.get(Slot));
                TotalCarTrips += odDemand.get(Slot);
            }
            System.out.println("Total car trips is = " + TotalCarTrips);
        }

    }
    public static void createTimeBins(){
        tb.put("AM",new Tuple<>(25200.,32400.));
        tb.put("IP",new Tuple<>(32400.,54000.));
        tb.put("PM",new Tuple<>(54000.,64800.));
        tb.put("OP_1",new Tuple<>(64800.,86400.));
        tb.put("OP_2",new Tuple<>(0.,25200.));
    }
//    public void TestingODDemandForCar{
//        AnaModODPairsImpl odPairs = new AnaModODPairsImpl(pop,net,ts,sc,tb,zones);
//        System.out.println("Total number of OD pairs " + odPairs.getOdPairs().size());
//
//        for(Id<AnaModODPair> id : odPairs.getOdPairs().keySet()){
//            int TotalCarTrips = 0;
//            Map<String, Double> odDemand = odPairs.getOdPairs().get(id).getOdDemand();
//            System.out.println("For OD " + id.toString());
//            System.out.println("Car Agents for this OD is = " + odPairs.getOdPairs().get(id).getCarAgents());
//            for(String Slot : odDemand.keySet()){
//                System.out.println("Demand at " + Slot  + " = "+odDemand.get(Slot));
//                TotalCarTrips += odDemand.get(Slot);
//            }
//            System.out.println("Total car trips is = " + TotalCarTrips);
//        }
//    }
    private static void createZones() throws ParseException {
        int zonesID = 1;
        for(SimpleFeature ft : ShapeFileReader.getAllFeatures(shapes+"SA2_studyarea_16_z55.shp")){
            GeometryFactory gf = new GeometryFactory();
            WKTReader reader = new WKTReader(gf);
            Geometry geometry = reader.read(ft.getAttribute("the_geom").toString());
            zones.put(zonesID,geometry);
            zonesID++;
        }
    }
}
