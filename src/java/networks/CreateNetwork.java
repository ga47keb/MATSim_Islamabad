package networks;

import com.pb.common.util.ResourceUtil;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.network.NetworkWriter;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.algorithms.NetworkCleaner;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.geometry.CoordinateTransformation;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;
import org.matsim.core.utils.io.OsmNetworkReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by carlloga on 16-05-17.
 */
public class CreateNetwork {

    public static void main (String args[]){

        CreateNetwork.createNetwork();

    }

    public static void createNetwork() {

        String networkFolder = "C:/3.Usman/MATSim_Islamabad/osmosis_network/osmosis_islnetwork//resources/";
        String osm = networkFolder + "input/islarea.osm";


        /*
         * The coordinate system to use. OpenStreetMap uses WGS84, but for MATSim, we need a projection where distances
         * are (roughly) euclidean distances in meters.
         *
         * UTM 33N is one such possibility (for parts of Europe, at least).
         *
         */
        CoordinateTransformation ct =
                TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, "EPSG:32643");

//        CoordinateTransformation ct =
//                TransformationFactory.getCoordinateTransformation(TransformationFactory.WGS84, TransformationFactory.WGS84);


        //31468 is the coordinate system DHDN_3 zone 4
        /*
         * First, create a new Config and a new Scenario. One always has to do this when working with the MATSim
         * data containers.
         *
         */
        Config config = ConfigUtils.createConfig();
        Scenario scenario = ScenarioUtils.createScenario(config);




        /*
         * Pick the Network from the Scenario for convenience.
         */
        Network network = scenario.getNetwork();

        OsmNetworkReader onr = new OsmNetworkReader(network,ct);
        onr.parse(osm);



        /*
         * Clean the Network. Cleaning means removing disconnected components, so that afterwards there is a route from every link
         * to every other link. This may not be the case in the initial network converted from OpenStreetMap.
         */
        new NetworkCleaner().run(network);




        /*
         * Write the Network to a MATSim network file.
         */
        new NetworkWriter(network).write(networkFolder + "output/matsimNetwork.xml.gz");

        System.out.println("MATSIM network created");


    }
}
