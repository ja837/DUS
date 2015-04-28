package test;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fvs.taxe.GameScreen;
import fvs.taxe.TaxeGame;
import fvs.taxe.controller.Context;
import fvs.taxe.replay.GiveGoalAction;
import gameLogic.Game;
import gameLogic.goal.Goal;
import gameLogic.map.Position;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class ReplayTest {
    Context context;
    Stage stage;
    Skin skin;
    TaxeGame taxeGame;
    Game gameLogic;
    GameScreen gameScreen;

    @Before
    public void setup() throws Exception {
    	
    	//skin = new Skin(Gdx.files.internal("data/uiskin.json"));    	
    	taxeGame = new TaxeGame();
    	gameLogic = new Game();
    	gameScreen = new GameScreen(taxeGame);
    	stage = gameScreen.getStage();
    	skin = gameScreen.getSkin();
    	
        context = new Context(stage, skin, taxeGame, gameLogic, gameScreen);
    }

    @Test
    public void giveGoalTest() throws Error {
    	
    	Station origin = new Station("station1", new Position(5, 5));
    	Station destination = new Station("station2", new Position(2, 2));
    	Station intermediary = new Station("station3", new Position(5, 5));
    	Train train = new Train("RedTrain", "RedTrain.png", "RedTrainRight.png", 250);
    	
    	Goal goal = new Goal(origin, destination, intermediary, 0, 4, 50, 20, train);
    	
    	
        GiveGoalAction giveGoalAction = new GiveGoalAction(context, 1, 1, goal);
        
        int initialNumberGoals = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getGoals().size();
        
        giveGoalAction.play();
        
        int numberGoalsAfterReplay = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getGoals().size();

        
        assertTrue("Replaying goal addition was not succesful", numberGoalsAfterReplay > initialNumberGoals);
        
    }


}
