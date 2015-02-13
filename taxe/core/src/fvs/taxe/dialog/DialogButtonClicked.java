package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import fvs.taxe.Button;
import fvs.taxe.StationClickListener;
import fvs.taxe.actor.TrainActor;
import fvs.taxe.controller.Context;
import fvs.taxe.controller.StationController;
import fvs.taxe.controller.TrainController;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.map.CollisionStation;
import gameLogic.map.Station;
import gameLogic.resource.Engineer;
import gameLogic.resource.Obstacle;
import gameLogic.resource.Skip;
import gameLogic.resource.Train;

public class DialogButtonClicked implements ResourceDialogClickListener {
    private Context context;
    private Player currentPlayer;
    private Train train;
    private Obstacle obstacle;
    private Skip skip;
    private Engineer engineer;

    public DialogButtonClicked(Context context, Player player, Train train) {
        this.currentPlayer = player;
        this.train = train;
        this.context = context;
        this.obstacle = null;
        this.skip=null;
        this.engineer = null;
    }
    public DialogButtonClicked(Context context, Player player, Obstacle obstacle){
        this.currentPlayer = player;
        this.train = null;
        this.skip = null;
        this.context = context;
        this.obstacle = obstacle;
        this.engineer = null;
    }
    public DialogButtonClicked(Context context, Player player, Skip skip){
        this.currentPlayer = player;
        this.train = null;
        this.skip = skip;
        this.context = context;
        this.obstacle = null;
    }

    public DialogButtonClicked(Context context, Player player, Engineer engineer){
        this.currentPlayer = player;
        this.train = null;
        this.engineer = engineer;
        this.context = context;
        this.obstacle = null;
        this.skip = null;
    }

    @Override
    public void clicked(Button button) {
        switch (button) {
            case TRAIN_DROP:
                currentPlayer.removeResource(train);
                break;
            case TRAIN_PLACE:{

                    Pixmap pixmap = new Pixmap(Gdx.files.internal(train.getCursorImage()));
                    Gdx.input.setCursorImage(pixmap, 0, 0); // these numbers will need tweaking
                    pixmap.dispose();

                    Game.getInstance().setState(GameState.PLACING_TRAIN);
                    TrainController trainController = new TrainController(context);
                    trainController.setTrainsVisible(null, false);
                    final StationClickListener stationListener = new StationClickListener() {
                        @Override
                        public void clicked(Station station) {
                            if (station instanceof CollisionStation) {
                                context.getTopBarController().displayFlashMessage("Trains cannot be placed at junctions.", Color.RED);
                                return;
                            }

                            train.setPosition(station.getLocation());
                            train.addHistory(station, Game.getInstance().getPlayerManager().getTurnNumber());

                            Gdx.input.setCursorImage(null, 0, 0);

                            TrainController trainController = new TrainController(context);
                            TrainActor trainActor = trainController.renderTrain(train);
                            trainController.setTrainsVisible(null, true);
                            train.setActor(trainActor);

                            StationController.unsubscribeStationClick(this);
                            Game.getInstance().setState(GameState.NORMAL);
                        }
                    };

                     final InputListener keyListener= new InputListener() {
                        @Override
                        public boolean keyDown (InputEvent event, int keycode) {
                            TrainController trainController = new TrainController(context);
                            trainController.setTrainsVisible(null, true);
                            Gdx.input.setCursorImage(null, 0, 0);
                            StationController.unsubscribeStationClick(stationListener);
                            Game.getInstance().setState(GameState.NORMAL);
                            context.getStage().removeListener(this);
                            return true;
                        }
                    };

                    context.getStage().addListener(keyListener);

                    StationController.subscribeStationClick(stationListener);
                break;}
            case TRAIN_ROUTE:
                context.getRouteController().begin(train);
                break;
            case VIEW_ROUTE:
                context.getRouteController().viewRoute(train);
                break;
            case OBSTACLE_DROP:
                currentPlayer.removeResource(obstacle);
                break;
            case OBSTACLE_USE:{
                    Pixmap pixmap = new Pixmap(Gdx.files.internal("BlockageCursor.png"));
                    Gdx.input.setCursorImage(pixmap, 0, 0); // these numbers will need tweaking
                    pixmap.dispose();
                    Game.getInstance().setState(GameState.PLACING_RESOURCE);
                    final TrainController trainController = new TrainController(context);
                    trainController.setTrainsVisible(null, false);
                    context.getTopBarController().displayFlashMessage("Placing Obstacle", Color.BLACK, 10000);


                    final StationClickListener stationListener = new StationClickListener() {
                        @Override
                        public void clicked(Station station) {
                            if (obstacle.getStation1() == null) {
                                obstacle.setStation1(station);
                            } else {
                                obstacle.setStation2(station);
                                if (context.getGameLogic().getMap().doesConnectionExist(obstacle.getStation1().getName(), obstacle.getStation2().getName())) {
                                    obstacle.use(context.getGameLogic().getMap().getConnection(obstacle.getStation1(), obstacle.getStation2()));
                                    currentPlayer.removeResource(obstacle);
                                    //If a train is already on the track it will move unopposed. This could be considered a feature of using the obstacle.
                                } else {
                                    Dialog dia = new Dialog("Invalid Selection", context.getSkin());
                                    dia.text("You have selected two stations which are not connected." +
                                            "\nPlease use the obstacle again.").align(Align.center);
                                    dia.button("OK", "OK");
                                    dia.show(context.getStage());
                                    obstacle.setStation1(null);
                                    obstacle.setStation2(null);
                                }
                                context.getTopBarController().displayFlashMessage("", Color.BLACK);
                                StationController.unsubscribeStationClick(this);
                                Gdx.input.setCursorImage(null, 0, 0);
                                context.getGameLogic().setState(GameState.NORMAL);
                                trainController.setTrainsVisible(null, true);
                            }
                        }
                    };
                    final InputListener keyListener = new InputListener() {
                        @Override
                        public boolean keyDown(InputEvent event, int keycode) {
                            TrainController trainController = new TrainController(context);
                            trainController.setTrainsVisible(null, true);
                            Gdx.input.setCursorImage(null, 0, 0);
                            StationController.unsubscribeStationClick(stationListener);
                            Game.getInstance().setState(GameState.NORMAL);
                            Gdx.input.setCursorImage(null, 0, 0);
                            context.getTopBarController().displayFlashMessage("", Color.BLACK, 0);
                            context.getStage().removeListener(this);
                            return true;
                        }
                    };
                    context.getStage().addListener(keyListener);
                    StationController.subscribeStationClick(stationListener);

                break;}
            case ENGINEER_USE: {
                Game.getInstance().setState(GameState.PLACING_RESOURCE);
                Pixmap pixmap = new Pixmap(Gdx.files.internal("engineer.png"));
                Gdx.input.setCursorImage(pixmap, 0, 0); // these numbers will need tweaking
                pixmap.dispose();
                final TrainController trainController = new TrainController(context);
                trainController.setTrainsVisible(null, false);
                context.getTopBarController().displayFlashMessage("Placing Engineer", Color.BLACK, 10000);
                final StationClickListener stationListener = new StationClickListener() {
                    @Override
                    public void clicked(Station station) {
                        if (engineer.getStation1() == null) {
                            engineer.setStation1(station);
                        } else {
                            engineer.setStation2(station);
                            if (context.getGameLogic().getMap().doesConnectionExist(engineer.getStation1().getName(), engineer.getStation2().getName())) {
                                if (context.getGameLogic().getMap().getConnection(engineer.getStation1(), engineer.getStation2()).isBlocked()) {
                                    engineer.use(context.getGameLogic().getMap().getConnection(engineer.getStation1(), engineer.getStation2()));
                                    currentPlayer.removeResource(engineer);
                                } else {
                                    Dialog dia = new Dialog("Invalid Selection", context.getSkin());
                                    dia.text("You have selected a connection which is not blocked." +
                                            "\nPlease use the engineer again.").align(Align.center);
                                    dia.button("OK", "OK");
                                    dia.show(context.getStage());
                                    engineer.setStation1(null);
                                    engineer.setStation2(null);
                                }
                            } else {
                                Dialog dia = new Dialog("Invalid Selection", context.getSkin());
                                dia.text("You have selected two stations which are not connected." +
                                        "\nPlease use the engineer again.").align(Align.center);
                                dia.button("OK", "OK");
                                dia.show(context.getStage());
                                engineer.setStation1(null);
                                engineer.setStation2(null);
                            }
                            context.getTopBarController().displayFlashMessage("", Color.BLACK,0);
                            StationController.unsubscribeStationClick(this);
                            Gdx.input.setCursorImage(null, 0, 0);
                            context.getGameLogic().setState(GameState.NORMAL);
                            trainController.setTrainsVisible(null, true);
                        }
                    }
                };
                StationController.subscribeStationClick(stationListener);

                final InputListener keyListener = new InputListener() {
                    @Override
                    public boolean keyDown(InputEvent event, int keycode) {
                        TrainController trainController = new TrainController(context);
                        trainController.setTrainsVisible(null, true);
                        Gdx.input.setCursorImage(null, 0, 0);
                        StationController.unsubscribeStationClick(stationListener);
                        Game.getInstance().setState(GameState.NORMAL);
                        context.getTopBarController().displayFlashMessage("", Color.BLACK, 0);
                        context.getStage().removeListener(this);
                        Gdx.input.setCursorImage(null, 0, 0);
                        return true;
                    }
                };
                this.context.getStage().addListener(keyListener);
                break;
            }
            case ENGINEER_DROP:
                currentPlayer.removeResource(engineer);
                break;
            case SKIP_RESOURCE:
                int p = context.getGameLogic().getPlayerManager().getCurrentPlayer().getPlayerNumber() - 1;
                if (p == 0){
                    p = 1;
                }else {
                    p = 0;
                }
                context.getGameLogic().getPlayerManager().getAllPlayers().get(p).setSkip(1);
                currentPlayer.removeResource(skip);
                break;
            case SKIP_DROP:
                currentPlayer.removeResource(skip);
                break;

            case TRAIN_CHANGE_ROUTE:
                context.getRouteController().begin2(train);
                break;
        }
    }
}
