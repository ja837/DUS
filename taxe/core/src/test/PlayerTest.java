package test;

import gameLogic.goal.Goal;
import gameLogic.goal.GoalManager;
import gameLogic.map.Map;
import gameLogic.player.Player;
import gameLogic.player.PlayerManager;
import gameLogic.resource.ResourceManager;
import gameLogic.resource.Train;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {
    private Player p;
    private PlayerManager pm;
    private Goal g;
    private GoalManager gm;
    private ResourceManager rs;

    @Before
    public void setUp() throws Exception {
        pm = new PlayerManager();
        pm.createPlayers(1);
        p = pm.getCurrentPlayer();
        rs = new ResourceManager();
        gm = new GoalManager(rs);
    }

    @Test
    public void removeGoal() throws Exception {
        gm.addRandomGoalToPlayer(p);
        assertEquals(1,p.getGoals().size());

        Goal g = p.getGoals().get(0);

        //remove goal player has
        p.removeGoal(g);
        assertEquals(0, p.getGoals().size());

        //remove goal player doesn't have
        p.removeGoal(g);
        assertEquals(0, p.getGoals().size());
    }

    public void getTrains() throws Exception {
        p.addResource(rs.getRandomTrain());
        assertTrue(p.getTrains().get(0) instanceof Train);
    }



}