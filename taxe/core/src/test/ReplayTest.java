package test;


import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fvs.taxe.GameScreen;
import fvs.taxe.TaxeGame;
import fvs.taxe.controller.Context;
import fvs.taxe.replay.DropGoalAction;
import fvs.taxe.replay.DropResourceAction;
import fvs.taxe.replay.GiveGoalAction;
import fvs.taxe.replay.GiveResourceAction;
import fvs.taxe.replay.PlaceTrainAction;
import fvs.taxe.replay.RouteTrainAction;
import fvs.taxe.replay.UseObstacleAction;
import gameLogic.Game;
import gameLogic.goal.Goal;
import gameLogic.map.Connection;
import gameLogic.map.Position;
import gameLogic.map.Station;
import gameLogic.resource.Obstacle;
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
    
    @Test
    public void dropGoalTest() throws Error {
    	
    	//Add a new goal first before we test removing it
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
        
        
        //Test dropping the goal
        DropGoalAction dropGoalAction =  new DropGoalAction(context, 1, 1, goal);
        
        initialNumberGoals = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getGoals().size();
        
        dropGoalAction.play();
        
        numberGoalsAfterReplay = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getGoals().size();

        
        assertTrue("Replaying goal removal was not succesful", numberGoalsAfterReplay < initialNumberGoals);
        
        
    }
    
    @Test
    public void giveResourceTest() throws Error {
    	
    	
    	Train train = new Train("RedTrain", "RedTrain.png", "RedTrainRight.png", 250);	
        GiveResourceAction giveResourceAction = new GiveResourceAction(context, 1, 1, train);
        
        int initialNumberResources = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getResources().size();
        
        giveResourceAction.play();
        
        int numberResourcesAfterReplay = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getResources().size();

        
        assertTrue("Replaying resource addition was not succesful", numberResourcesAfterReplay > initialNumberResources);
        
    }
    
    @Test
    public void removeResourceTest() throws Error {
    	
    	//Add a resource before we test removing it
    	Train train = new Train("RedTrain", "RedTrain.png", "RedTrainRight.png", 250);	
        GiveResourceAction giveResourceAction = new GiveResourceAction(context, 1, 1, train);        
        int initialNumberResources = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getResources().size();        
        giveResourceAction.play();        
        int numberResourcesAfterReplay = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getResources().size();
   
        assertTrue("Replaying resource addition was not succesful", numberResourcesAfterReplay > initialNumberResources);
        
        
        DropResourceAction dropResourceAction = new DropResourceAction(context, 1, 1, train);
        
        initialNumberResources = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getResources().size();        
        dropResourceAction.play();        
        numberResourcesAfterReplay = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getResources().size();
        
        assertTrue("Replaying resource removal was not succesful", numberResourcesAfterReplay < initialNumberResources);
        
    }
    
    @Test
    public void placeTrainTest() throws Error {
    	
    	//Place a train at a station
    	Train train = new Train("RedTrain", "RedTrain.png", "RedTrainRight.png", 250);	
    	Station station = new Station("station1", new Position(5, 5));
    	
        PlaceTrainAction placeTrainAction = new PlaceTrainAction(context, 1, train, station);    
                 
        placeTrainAction.play();        
   
        assertTrue("Placing Train was not succesful", train.isDeployed());
        
        
    }
    
    @Test
    public void routingTrainTest() throws Error {
    	
    	Train train = new Train("RedTrain", "RedTrain.png", "RedTrainRight.png", 250);	
    	Station station = new Station("station1", new Position(5, 5));
    	Station station2 = new Station("station1", new Position(6, 6));
    	
    	//Place train before it can be routed
        PlaceTrainAction placeTrainAction = new PlaceTrainAction(context, 1, train, station);    
        placeTrainAction.play(); 
        assertTrue("Placing Train was not succesful", train.isDeployed());
        
        //Now route the train
        ArrayList<Station> route = new ArrayList<Station>();
    	route.add(station);
    	route.add(station2);
                 
        RouteTrainAction routeTrainAction = new RouteTrainAction(context, 1, train, route);  
        routeTrainAction.play();
        
        assertTrue("Routing Train was not succesful", train.getRoute().size() > 0);

        
    }
    
    @Test
    public void usePowerupTest() throws Error {
    	
    	//Give a player the powerup before they can use it
    	Obstacle o = new Obstacle();
        GiveResourceAction giveResourceAction = new GiveResourceAction(context, 1, 1, o);
        
        int initialNumberResources = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getResources().size();        
        giveResourceAction.play();        
        int numberResourcesAfterReplay = context.getGameLogic().getPlayerManager().getAllPlayers().get(0).getResources().size();
        assertTrue("Replaying resource addition was not succesful", numberResourcesAfterReplay > initialNumberResources);
    	
        
        //Then use powerup on random connection
    	Connection c = context.getMainGame().getMap().getRandomConnection();
    	
    	UseObstacleAction useObstacleAction = new UseObstacleAction(context, 1, c);
    	
    	int initialTurnsBlocked = c.getTurnsBlocked();
    	useObstacleAction.play();
    	int afterTurnsBlocked = c.getTurnsBlocked();
        
        assertTrue("Routing Train was not succesful", afterTurnsBlocked > initialTurnsBlocked);

        
    }



}
