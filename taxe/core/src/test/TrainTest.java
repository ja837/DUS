package test;


import gameLogic.Game;
import gameLogic.map.Position;
import gameLogic.map.Station;
import gameLogic.resource.Train;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TrainTest extends LibGdxTest {
    Train train;

    @Before
    public void trainSetup() throws Exception {
        train = new Train("RedTrain", "RedTrain.png", "RedTrainRight.png", 250);
    }

    @Test
    public void finalDestinationTest() throws Error {
        Station station1 = new Station("station1", new Position(5, 5));
        Station station2 = new Station("station2", new Position(6, 6));
        ArrayList<Station> route = new ArrayList<Station>();
        route.add(station1);
        route.add(station2);

        train.setRoute(route);
        assertTrue("Setting a train route was not succesful", train.getRoute().size() == 2);
        assertTrue("Final destination wasn't set", train.getFinalDestination() == station2);
    }

    @Test
    public void testLastStation() throws Error {
        ArrayList<Station> route = new ArrayList<Station>();
        Station a = Game.getInstance().getMap().getStationByName("Madrid");
        Station b = Game.getInstance().getMap().getStationByName("Paris");
        Station c = Game.getInstance().getMap().getStationByName("Brussels");
        route.add(a);
        route.add(b);
        route.add(c);
        train.setRoute(route);
        //no movement
        assertEquals("Madrid",train.getLastStation().getName());

        //after movement
        train.addHistory(a,1);
        train.addHistory(b,1);
        assertEquals("Paris", train.getLastStation().getName());
    }

    @Test
    public void testNextStation() throws Error {
        ArrayList<Station> route = new ArrayList<Station>();
        Station a = Game.getInstance().getMap().getStationByName("Madrid");
        Station b = Game.getInstance().getMap().getStationByName("Paris");
        Station c = Game.getInstance().getMap().getStationByName("Brussels");
        route.add(a);
        route.add(b);
        route.add(c);
        train.setRoute(route);

        train.addHistory(a,1);
        assertEquals("Paris",train.getNextStation().getName());
    }


}
