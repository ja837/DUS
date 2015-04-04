package gameLogic;

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

import java.util.ArrayList;
import java.util.List;

public class Game {
    //This is sort of a super-class that can be accessed throughout the system as many of its methods are static
    //This is a useful tool to exploit to make implementing certain features easier
    private static Game instance;
    private PlayerManager playerManager;
    private GoalManager goalManager;
    private ResourceManager resourceManager;
    private ReplayManager replayManager;
    private Map map;
    private GameState state;
    private boolean replaying = false;


	private List<GameStateListener> gameStateListeners = new ArrayList<GameStateListener>();

    //Number of players, not sure how much impact this has on the game at the moment but if you wanted to add more players you would use this attributes
    private final int CONFIG_PLAYERS = 2;

    //This
    public final int TOTAL_TURNS = 30;
    public final int MAX_POINTS = 10000;

    private Game() {
        //Creates players
        playerManager = new PlayerManager();
        playerManager.createPlayers(CONFIG_PLAYERS);

        //Give them starting resources and goals
        resourceManager = new ResourceManager();
        goalManager = new GoalManager(resourceManager);
        
        //Init replayManager
        replayManager = new ReplayManager();

        map = new Map();

        state = GameState.NORMAL;

        playerManager.subscribeTurnChanged(new TurnListener() {
            @Override
            public void changed() {
                //Moved all the adding of resources ad goals to GameScreen to enable replay handling - Jamie
            }
        });
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

    
    public boolean isReplaying() {
		return replaying;
	}

	public void setReplaying(boolean replaying) {
		this.replaying = replaying;
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
}