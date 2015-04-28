package gameLogic;


import fvs.taxe.SoundManager;
import fvs.taxe.controller.Context;
import fvs.taxe.replay.EndTurnAction;
import fvs.taxe.replay.GiveResourceAction;
import fvs.taxe.replay.ReplayManager;
import gameLogic.listeners.GameStateListener;
import gameLogic.listeners.TurnListener;
import gameLogic.goal.GoalManager;
import gameLogic.map.Map;
import gameLogic.player.Player;
import gameLogic.player.PlayerManager;
import gameLogic.resource.Resource;
import gameLogic.resource.ResourceManager;
import gameLogic.resource.Train;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.TimeUtils;

public class Game {
    //This is sort of a super-class that can be accessed throughout the system as many of its methods are static
    //This is a useful tool to exploit to make implementing certain features easier
    private static Game instance;
    private PlayerManager playerManager;
    private GoalManager goalManager;
    private ResourceManager resourceManager;
    private ReplayManager replayManager;
    private SoundManager soundManager;
    private Map map;
    private GameState state;



	private List<GameStateListener> gameStateListeners = new ArrayList<GameStateListener>();

    //Number of players, not sure how much impact this has on the game at the moment but if you wanted to add more players you would use this attributes
    private final int CONFIG_PLAYERS = 2;

    //This
    public static int TOTAL_TURNS = 30;
    public final int MAX_POINTS = 10000;

    public Game() {
        //Creates players
        playerManager = new PlayerManager();
        playerManager.createPlayers(CONFIG_PLAYERS);

        //Give them starting resources and goals
        resourceManager = new ResourceManager();
        goalManager = new GoalManager(resourceManager);
        
        soundManager = new SoundManager();
        soundManager.playBGMusic();
        
        replayManager = new ReplayManager();
        
        map = new Map();

        state = GameState.NORMAL;

        playerManager.subscribeTurnChanged(new TurnListener() {
            @Override
            public void changed() {
                //Moved all the adding of resources and goals to GameScreen to enable replay handling - Jamie
            }
        });
    }
    
    public void printDebugInfo(){
    	System.out.println("Turn" + playerManager.getTurnNumber());
    	System.out.println("\n" + playerManager.getAllPlayers().get(0).toString() + "\n");
    	for (Resource r : playerManager.getAllPlayers().get(0).getResources()){
    		if (r instanceof Train){
    			Train t = (Train) r;
    			if (t.getActor() != null){
    				System.out.println("Train visible = " + t.getActor().isVisible());
    			}
    			
    		}
    		else{
    			System.out.println(r.toString());
    		}
    		
    	}
    	
    	System.out.println("\n" + playerManager.getAllPlayers().get(1).toString() + "\n");
    	for (Resource r : playerManager.getAllPlayers().get(1).getResources()){
    		if (r instanceof Train){
    			Train t = (Train) r;
    			if (t.getActor() != null){
    				System.out.println("Train visible = " + t.getActor().isVisible());
    			}
    			
    		}
    		else{
    			System.out.println(r.toString());
    		}
    	}
    }

    

	public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
            // initialisePlayers gives them a goal, and the GoalManager requires an instance of game to exist so this
            // method can't be called in the constructor
            
            //Moved init players to game screen to allow for easy replay action logging.
            //instance.initialisePlayers();
        }

        return instance;
    }

	
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GoalManager getGoalManager() {
        return goalManager;
    }
    
    public ResourceManager getResourceManager() {
		return resourceManager;
	}
    
    public ReplayManager getReplayManager() {
		return replayManager;
	}
    
    public void setReplayManager(ReplayManager replayManager) {
		this.replayManager = replayManager;
	}

    public SoundManager getSoundManager(){
    	return soundManager;
    }
    public Map getMap() {
        return map;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
        //Informs all listeners that the state has changed
        stateChanged();
    }

    public void subscribeStateChanged(GameStateListener listener) {
        gameStateListeners.add(listener);
    }

    private void stateChanged() {
        for (GameStateListener listener : gameStateListeners) {
            listener.changed(state);
        }
    }

	public static void changeTurns(int n) {
		TOTAL_TURNS = n;
		
	}
}