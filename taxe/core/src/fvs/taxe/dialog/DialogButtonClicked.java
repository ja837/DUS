package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import fvs.taxe.Button;
import fvs.taxe.StationClickListener;
import fvs.taxe.actor.TrainActor;
import fvs.taxe.controller.Context;
import fvs.taxe.controller.StationController;
import fvs.taxe.controller.TrainController;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.PlayerManager;
import gameLogic.map.CollisionStation;
import gameLogic.map.Station;
import gameLogic.resource.Obstacle;
import gameLogic.resource.Train;

public class DialogButtonClicked implements ResourceDialogClickListener {
    private Context context;
    private Player currentPlayer;
    private Train train;
    private Obstacle obstacle;
    public DialogButtonClicked(Context context, Player player, Train train) {
        this.currentPlayer = player;
        this.train = train;
        this.context = context;
        this.obstacle = null;
    }
    public DialogButtonClicked(Context context, Player player, Obstacle obstacle){
        this.currentPlayer = player;
        this.train = null;
        this.context = context;
        this.obstacle = obstacle;
    }

    @Override
    public void clicked(Button button) {
        switch (button) {
            case TRAIN_DROP:
                currentPlayer.removeResource(train);
                break;
            case TRAIN_PLACE:
                Pixmap pixmap = new Pixmap(Gdx.files.internal(train.getCursorImage()));
                Gdx.input.setCursorImage(pixmap, 10, 25); // these numbers will need tweaking
                pixmap.dispose();

                Game.getInstance().setState(GameState.PLACING);
                TrainController trainController = new TrainController(context);
                trainController.setTrainsVisible(null, false);

                StationController.subscribeStationClick(new StationClickListener() {
                    @Override
                    public void clicked(Station station) {
                    	if(station instanceof CollisionStation) {
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
                });

                break;
            case TRAIN_ROUTE:
                context.getRouteController().begin(train);
                break;
            case VIEW_ROUTE:
                context.getRouteController().viewRoute(train);
                break;
            case OBSTACLE_DROP:
                currentPlayer.removeResource(obstacle);
                break;
            case OBSTACLE_USE:
                //Enter how to use the obstacle here
                break;
            case SKIP_RESOURCE:
                context.getGameLogic().getPlayerManager().skipTurnResource();
                break;

            case TRAIN_CHANGE_ROUTE:
                context.getRouteController().begin2(train);
                break;
        }
    }
}
