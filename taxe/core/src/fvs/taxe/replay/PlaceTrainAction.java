package fvs.taxe.replay;

import com.badlogic.gdx.Gdx;

import fvs.taxe.actor.TrainActor;
import fvs.taxe.controller.Context;
import fvs.taxe.controller.StationController;
import fvs.taxe.controller.TrainController;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.Station;
import gameLogic.player.Player;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

public class PlaceTrainAction extends Action {
	
	
	Train train;
	Station station;

	public PlaceTrainAction(Context context, long timestamp, Train t, Station s) {
		super(context, timestamp);
		
		
		train  = t;
		station = s;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an train placement action.");
		
		Player p = train.getPlayer();
		int playernumber = p.getPlayerNumber();
		
		for (Resource r : context.getGameLogic().getPlayerManager().getAllPlayers().get(playernumber - 1).getResources()){
			if (r instanceof Train){
				Train t = (Train) r;
				if (t.getName().equals(train.getName())){
					train = t;
				}
			}
		}

		//This puts the train at the station that the user clicks and adds it to the trains visited history
        train.setPosition(station.getLocation());
        train.addHistory(station, context.getGameLogic().getPlayerManager().getTurnNumber());
        train.setReplay(true);

        //Hides the current train but makes all moving trains visible
        TrainController trainController = new TrainController(context);
        TrainActor trainActor = trainController.renderTrain(train);
        trainController.setTrainsVisible(null, true);
        train.setActor(trainActor);
        train.setDeployed(true);
        train.setReplay(true);

	}

	@Override
	public String toString() {
		
		return "Place Train Action, placing " + train.toString() + " at " + station.toString() + super.toString();
	}

}
