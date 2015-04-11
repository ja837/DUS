package fvs.taxe.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fvs.taxe.GameScreen;
import fvs.taxe.TaxeGame;
import fvs.taxe.replay.GiveGoalAction;
import fvs.taxe.replay.GiveResourceAction;
import fvs.taxe.replay.PlaceObstacleAction;
import fvs.taxe.replay.ReplayManager;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.goal.Goal;
import gameLogic.listeners.TurnListener;
import gameLogic.map.Connection;
import gameLogic.player.Player;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

public class Context {
    //Context appears to be a class that allows different aspects of the system access parts that they otherwise logically shouldn't have access to.
    //While this is a bit of a workaround to make implementation easier, it does weaken encapsulation somewhat, however a full system overhaul would be unfeasible to remedy this.
    private TaxeGame taxeGame;
    private Stage stage;
    private Skin skin;
    private Game gameLogic;
    private Game replayingGame;
   
	private GameScreen gameScreen;

	private RouteController routeController;
    private TopBarController topBarController;
    

    public Context(Stage stage, Skin skin, TaxeGame taxeGame, Game gameLogic, GameScreen gameScreen) {
        this.stage = stage;
        this.skin = skin;
        this.taxeGame = taxeGame;
        this.gameLogic = gameLogic;
        this.replayingGame = new Game();
        this.gameScreen = gameScreen;
    }

    public GameScreen getGameScreen() {
		return gameScreen;
	}

	//Getters and setters: pretty self-explanatory
    public Stage getStage() {
        return stage;
    }
    

    public Skin getSkin() {
        return skin;
    }

    public TaxeGame getTaxeGame() {
        return taxeGame;
    }

    public Game getGameLogic() {
    	if (gameLogic.getReplayManager().isReplaying()){
    		return replayingGame;
    	}
    	else{
    		return gameLogic;
    	}
        
    }
    
    public Game getReplayingGame() {
		return replayingGame;
	}
    
    public Game getMainGame(){
    	return gameLogic;
    }

    
    public ReplayManager getReplayManager(){
    	return gameLogic.getReplayManager();
    }
    
    public RouteController getRouteController() {
        return routeController;
    }

    public void setRouteController(RouteController routeController) {
        this.routeController = routeController;
    }

    public TopBarController getTopBarController() {
        return topBarController;
    }

    public void setTopBarController(TopBarController topBarController) {
        this.topBarController = topBarController;
    }
    
    public void enterReplay(){
    	
    	if (gameLogic.getReplayManager().getTimeSinceReplayStarted() > 0){
    		//Replay has been started before
    		gameLogic.getReplayManager().resumeReplay();
    	}else{
    		gameLogic.getReplayManager().startReplay();
    		
    		replayingGame.getPlayerManager().subscribeTurnChanged(new TurnListener() {
    			@Override
    			public void changed() {
    				
    				replayingGame.getMap().decrementBlockedConnections();
    				
    				if (replayingGame.getPlayerManager().getTurnNumber()!=1) {
    					replayingGame.setState(GameState.ANIMATING);
    					topBarController.displayFlashMessage("Time is passing...", Color.BLACK);
    				}

    				//System.out.println("Replaying game info:\n");
    				//replayingGame.printDebugInfo();

    			}
    		});
    	}
    	
    	
    	
    	//Remove all the non replay actors
    	for (Resource r : gameLogic.getPlayerManager().getAllPlayers().get(0).getResources()){
    		if (r instanceof Train){
    			Train t = (Train) r;
    			if (t.getActor() != null){
    				t.getActor().remove();
    			}
    		}
    	}
    	for (Resource r : gameLogic.getPlayerManager().getAllPlayers().get(1).getResources()){
    		if (r instanceof Train){
    			Train t = (Train) r;
    			if (t.getActor() != null){
    				t.getActor().remove();
    			}
    		}
    	}
    	
    	TrainController controller = new TrainController(this);
    	controller.setTrainsVisible(null, true);
    	
    	gameScreen.show();
    	
    }    
    
    public void exitReplay(){
    	gameLogic.getReplayManager().pauseReplay();
    	gameLogic.getReplayManager().endReplay();
    	gameScreen.show();
    	TrainController controller = new TrainController(this);
    	controller.setTrainsVisible(null, true);
    	
    	//Add all the non replay actors back
    	for (Resource r : gameLogic.getPlayerManager().getAllPlayers().get(0).getResources()){
    		if (r instanceof Train){
    			Train t = (Train) r;
    			if (t.getActor() != null){
    				getStage().addActor(t.getActor());
    			}
    		}
    	}
    	for (Resource r : gameLogic.getPlayerManager().getAllPlayers().get(1).getResources()){
    		if (r instanceof Train){
    			Train t = (Train) r;
    			if (t.getActor() != null){
    				getStage().addActor(t.getActor());
    			}
    		}
    	}
    }
    
    public void restartReplay(){
    	
    	//Remove all the old replay actors
    	for (Resource r : replayingGame.getPlayerManager().getAllPlayers().get(0).getResources()){
    		if (r instanceof Train){
    			Train t = (Train) r;
    			if (t.getActor() != null){
    				t.getActor().remove();
    			}
    		}
    	}
    	for (Resource r : replayingGame.getPlayerManager().getAllPlayers().get(1).getResources()){
    		if (r instanceof Train){
    			Train t = (Train) r;
    			if (t.getActor() != null){
    				t.getActor().remove();
    			}
    		}
    	}
    	
    	replayingGame = new Game();
    	gameLogic.getReplayManager().startReplay();
    	
    	replayingGame.getPlayerManager().subscribeTurnChanged(new TurnListener() {
			@Override
			public void changed() {
				
				replayingGame.getMap().decrementBlockedConnections();
				
				if (replayingGame.getPlayerManager().getTurnNumber()!=1) {
					replayingGame.setState(GameState.ANIMATING);
					topBarController.displayFlashMessage("Time is passing...", Color.BLACK);
				}

				//System.out.println("Replaying game info:\n");
				//replayingGame.printDebugInfo();

			}
		});
    }
    

}
