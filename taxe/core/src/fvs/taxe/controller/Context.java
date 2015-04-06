package fvs.taxe.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fvs.taxe.GameScreen;
import fvs.taxe.TaxeGame;
import fvs.taxe.replay.ReplayManager;
import gameLogic.Game;

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
    
    public void startReplay(){
    	gameLogic.getReplayManager().startReplay();
    }    
    
    public void endReplay(){
    	gameLogic.getReplayManager().endReplay();
    	gameScreen.show();
    }
    

}
