package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fvs.taxe.controller.*;
import fvs.taxe.dialog.DialogEndGame;
import fvs.taxe.replay.GiveGoalAction;
import fvs.taxe.replay.GiveResourceAction;
import fvs.taxe.replay.ReplayManager;
import fvs.taxe.replay.UseObstacleAction;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.goal.Goal;
import gameLogic.listeners.GameStateListener;
import gameLogic.listeners.TurnListener;
import gameLogic.map.Connection;
import gameLogic.map.Map;
import gameLogic.player.Player;
import gameLogic.resource.Resource;


public class GameScreen extends ScreenAdapter {
    final private TaxeGame game;
    private Stage stage;
    private Texture mapTexture;
    private Game gameLogic;
    private Skin skin;
    private Map map;
    private float timeAnimated = 0;
    public static final int ANIMATION_TIME = 2;
    private Tooltip tooltip;
    private Context context;

    private StationController stationController;
    private TopBarController topBarController;
    private ResourceController resourceController;
    private GoalController goalController;
    private RouteController routeController;
    
    private ReplayManager replayManager;

    public GameScreen(TaxeGame game) {
        this.game = game;
        stage = new Stage();

        //Sets the skin
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        //Initialises the game
        gameLogic = Game.getInstance();
        replayManager = gameLogic.getReplayManager();
        
        //Moved this here from Game.java to give access to things needed for replay system.
        initialisePlayers();
        
        context = new Context(stage, skin, game, gameLogic);
        Gdx.input.setInputProcessor(stage);

        //Draw background
        mapTexture = new Texture(Gdx.files.internal("gamemap.png"));
        map = gameLogic.getMap();
        

        tooltip = new Tooltip(skin);
        stage.addActor(tooltip);

        //Initialises all of the controllers for the UI
        stationController = new StationController(context, tooltip);
        topBarController = new TopBarController(context);
        resourceController = new ResourceController(context);
        goalController = new GoalController(context);
        routeController = new RouteController(context);
        context.setRouteController(routeController);
        context.setTopBarController(topBarController);

        //Adds a listener that displays a flash message whenever the turn ends
        gameLogic.getPlayerManager().subscribeTurnChanged(new TurnListener() {
            @Override
            public void changed() {
                //The game will not be set into the animating state for the first turn to prevent player 1 from gaining an inherent advantage by gaining an extra turn of movement.
                if (context.getGameLogic().getPlayerManager().getTurnNumber()!=1) {
                    gameLogic.setState(GameState.ANIMATING);
                    topBarController.displayFlashMessage("Time is passing...", Color.BLACK);
                }
                
                //Adds all the subscriptions to the game which gives players resources and goals at the start of each turn.
                //Also decrements all connections and blocks a random one
                //The checking for whether a turn is being skipped is handled inside the methods, this just always calls them
                
                Player currentPlayer = gameLogic.getPlayerManager().getCurrentPlayer();
                Goal goal = gameLogic.getGoalManager().addRandomGoalToPlayer(currentPlayer);
                Resource resource1 = gameLogic.getResourceManager().addRandomResourceToPlayer(currentPlayer);
                Resource resource2 = gameLogic.getResourceManager().addRandomResourceToPlayer(currentPlayer);
                map.decrementBlockedConnections();
                Connection blockedConnection = map.blockRandomConnection();
                
                //Record the actions that happen at the end of a turn in the replay manager.
                if (!gameLogic.getState().isReplaying()){
                	               	
                	if (resource1 != null){
                		GiveResourceAction resourceAction = new GiveResourceAction(context, replayManager.getCurrentTimeStamp(), currentPlayer, resource1);
                		replayManager.addAction(resourceAction);
                	}
                    
                	if (resource2 != null){
                		GiveResourceAction resourceAction2 = new GiveResourceAction(context, replayManager.getCurrentTimeStamp(), currentPlayer, resource2);
                		replayManager.addAction(resourceAction2);
                	}
                	
                	if (goal != null){
                		GiveGoalAction goalAction =  new GiveGoalAction(context, replayManager.getCurrentTimeStamp(), currentPlayer, goal);                   
                        replayManager.addAction(goalAction);
                	}
                	
                	if (blockedConnection != null){
                		UseObstacleAction obstacleAction =  new UseObstacleAction(context, replayManager.getCurrentTimeStamp(), null, null, blockedConnection);                   
                        replayManager.addAction(obstacleAction);
                	}
                    
                    
                    
                    replayManager.printDebugInfo();
                }
                
            }
        });
        



        //Adds a listener that checks certain conditions at the end of every turn
        gameLogic.subscribeStateChanged(new GameStateListener() {
            @Override
            public void changed(GameState state) {
                if ((gameLogic.getPlayerManager().getTurnNumber() == gameLogic.TOTAL_TURNS || gameLogic.getPlayerManager().getCurrentPlayer().getScore() >= gameLogic.MAX_POINTS) && state == GameState.NORMAL) {
                    //If the game should end due to the turn number or points total then the appropriate dialog is displayed
                    DialogEndGame dia = new DialogEndGame(GameScreen.this.game, gameLogic.getPlayerManager(), skin);
                    dia.show(stage);
                } else if (gameLogic.getState() == GameState.ROUTING || gameLogic.getState() == GameState.PLACING_TRAIN) {
                    //If the player is routing or place a train then the goals and nodes are colour coded
                    goalController.setColours(StationController.colours);
                } else if (gameLogic.getState() == GameState.NORMAL) {
                    //If the game state is normal then the goal colour are reset to grey
                    goalController.setColours(new Color[3]);
                }
            }
        });
    }


    // called every frame
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //Draws the map background
        game.batch.draw(mapTexture, 0, 0);
        game.batch.end();

        topBarController.drawBackground();

        stationController.renderConnections(map.getConnections(), Color.GRAY);
        if (gameLogic.getState() == GameState.PLACING_TRAIN || gameLogic.getState() == GameState
                .ROUTING) {
            stationController.renderStationGoalHighlights();
            //This colours the start and end nodes of each goal to allow the player to easily see where they need to route
        }

        //Draw routing
        if (gameLogic.getState() == GameState.ROUTING) {
            routeController.drawRoute(Color.BLACK);

        } else
            //Draw train moving
            if (gameLogic.getState() == GameState.ANIMATING) {
                timeAnimated += delta;
                if (timeAnimated >= ANIMATION_TIME) {
                    gameLogic.setState(GameState.NORMAL);
                    timeAnimated = 0;
                }
            }

        //Draw the number of trains at each station
        if (gameLogic.getState() == GameState.NORMAL || gameLogic.getState() == GameState.PLACING_TRAIN) {
            stationController.displayNumberOfTrainsAtStations();
        }

        //Causes all the actors to perform their actions (i.e trains to move)
        stage.act(Gdx.graphics.getDeltaTime());

        stage.draw();

        game.batch.begin();
        //If statement checks whether the turn is above 30, if it is then display 30 anyway
        game.fontSmall.draw(game.batch, "Turn " + ((gameLogic.getPlayerManager().getTurnNumber() + 1 < gameLogic.TOTAL_TURNS) ? gameLogic.getPlayerManager().getTurnNumber() + 1 : gameLogic.TOTAL_TURNS) + "/" + gameLogic.TOTAL_TURNS, (float) TaxeGame.WIDTH - 90.0f, 20.0f);
        game.batch.end();

        resourceController.drawHeaderText();
        goalController.drawHeaderText();
    }

    @Override
    // Called when GameScreen becomes current screen of the game
    public void show() {
        //We only render this once a turn, this allows the buttons generated to be clickable.
        //Initially some of this functionality was in the draw() routine, but it was found that when the player clicked on a button a new one was rendered before the input could be handled
        //This is why the header texts and the buttons are rendered separately, to prevent these issues from occuring
        stationController.renderStations();
        topBarController.addEndTurnButton();
        goalController.showCurrentPlayerGoals();
        resourceController.drawPlayerResources(gameLogic.getPlayerManager().getCurrentPlayer());
    }


    @Override
    public void dispose() {
        mapTexture.dispose();
        stage.dispose();
    }
    
    // Only the first player should be given goals and resources during init
    // The second player gets them when turn changes!
    // Moved here from Game.java to give access to replay system
    private void initialisePlayers() {
        Player player = gameLogic.getPlayerManager().getAllPlayers().get(0);
        Goal goal = gameLogic.getGoalManager().addRandomGoalToPlayer(player);
        Resource resource1 = gameLogic.getResourceManager().addRandomResourceToPlayer(player);
        Resource resource2 = gameLogic.getResourceManager().addRandomResourceToPlayer(player);
        
        if (!gameLogic.getState().isReplaying()){
        	//Record actions for replay
            GiveResourceAction resourceAction = new GiveResourceAction(context, replayManager.getCurrentTimeStamp(), player, resource1);
            GiveResourceAction resourceAction2 = new GiveResourceAction(context, replayManager.getCurrentTimeStamp(), player, resource2);
            GiveGoalAction goalAction =  new GiveGoalAction(context, replayManager.getCurrentTimeStamp(), player, goal);
                                           
            replayManager.addAction(resourceAction);
            replayManager.addAction(resourceAction2);
            replayManager.addAction(goalAction);
        }
        
    }

}