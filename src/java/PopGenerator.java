import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.utils.geometry.transformations.TransformationFactory;

public class PopGenerator {


    public Population createASP(Population population){

        PopulationFactory populationFactory = population.getFactory();

        for (int i = 0; i <10; i++) {
            Person person = populationFactory.createPerson(Id.createPersonId(i));

            Plan plan = populationFactory.createPlan();

            //Coord homeCoordinates = new Coord(33.591757, 73.063375);
            Coord homeCoordinates = new Coord(320230.919072, 3721889.406729);
            Activity homeActivity = populationFactory.createActivityFromCoord("home", homeCoordinates);
            homeActivity.setEndTime(8 * 3600 + Math.random() * 3600);
            plan.addActivity(homeActivity);

            plan.addLeg(populationFactory.createLeg(TransportMode.car));

            //Coord workCoordinates = new Coord(33.700833, 72.979489);
            Coord workCoordinates = new Coord(312243.928772,3729146.583074);
            Activity workActivity = populationFactory.createActivityFromCoord("work", workCoordinates);
            workActivity.setEndTime(16 * 3600 + Math.random() * 3600);
            plan.addActivity(workActivity);

            plan.addLeg(populationFactory.createLeg(TransportMode.car));

            Activity homeActivity2 = populationFactory.createActivityFromCoord("home", homeCoordinates);
            plan.addActivity(homeActivity2);


            person.addPlan(plan);
            population.addPerson(person);
        }




        return population;
    }
}
