package test;

import gameLogic.Game;
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

public class PlayerTest extends LibGdxTest {
    private Player p;
    private PlayerManager pm;
    private GoalManager gm;
    private ResourceManager rs;
    private Map map;

    /*@Before
    public void setUp() throws Exception {
        Game game = Game.getInstance();
        game.getPlayerManager();
        pm = game.getPlayerManager();
        pm.createPlayers(1);
        p = pm.getCurrentPlayer();
        rs = new ResourceManager();
        game.getGoalManager();
        gm = game.getGoalManager();
        map = new Map();

    }*/

    @Test
    public void removeGoal() throws Exception {
        Player p = Game.getInstance().getPlayerManager().getAllPlayers().get(0);
        assertEquals(1, p.getGoals().size());

        Goal g = p.getGoals().get(0);

        //remove goal player has
        p.removeGoal(g);
        assertEquals(0, p.getGoals().size());

        //remove goal player doesn't have
        p.removeGoal(g);
        assertEquals(0, p.getGoals().size());
    }

    @Test
    public void getTrains() throws Exception {
        Player p = Game.getInstance().getPlayerManager().getAllPlayers().get(0);

        Train t = new Train("x","y","z",1);
        p.addResource(t);
        assertTrue(p.getTrains().get(0) instanceof Train);
    }



}