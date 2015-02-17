package test;


import gameLogic.player.Player;
import gameLogic.player.PlayerManager;
import gameLogic.goal.Goal;
import gameLogic.goal.GoalManager;
import gameLogic.map.Position;
import gameLogic.map.Station;
import gameLogic.resource.ResourceManager;
import gameLogic.resource.Train;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class GoalManagerTest extends LibGdxTest {
    GoalManager gm;
    PlayerManager pm;

    @Before
    public void setup() throws Exception {
        ResourceManager rs = new ResourceManager();
        gm = new GoalManager(rs);
        pm = new PlayerManager();

    }

    @Test
    public void goalManagerTest() throws Exception {
        pm.createPlayers(2);
        Player player1 = pm.getCurrentPlayer();

        Train train = new Train("Green", "", "", 100);

        Station station1 = new Station("station1", new Position(5, 5));
        Station station2 = new Station("station2", new Position(2, 2));
        Station station3 = new Station("station3", new Position(6, 2));

        Goal goal = new Goal(station1, station2, station3, 0, 0, 0, 0, train);
        player1.addGoal(goal);
        player1.addResource(train);

        ArrayList<Station> route = new ArrayList<Station>();
        route.add(station1);
        route.add(station2);
        train.setRoute(route);

//        train.addHistory("station1", 0);

        pm.turnOver(null);
        pm.turnOver(null);
//        train.addHistory("station2", 1);

        ArrayList<String> completedStrings = gm.trainArrived(train, player1);
        assertFalse(goal.isComplete(train)); //Goal wasn't completed
        assertFalse(completedStrings.size() > 0); //Completed goal string not right

    }
}
