package fvs.taxe.replay;



import fvs.taxe.actor.TrainActor;
import fvs.taxe.controller.Context;
import fvs.taxe.controller.TrainController;
import gameLogic.map.Station;
import gameLogic.player.Player;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

public class PlaceTrainAction extends Action {
	
	
	Train train;
	Station station;

	public PlaceTrainAction(Context context, long timestamp, Train t, Station s) {
		super(context, timestamp);
				
		train = t;	
		station = s;
	}

	@Override
	public void play() {
		System.out.println("Replaying an train placement action.");
		
		//Find the player and then the train that is going to be placed.
		Player p = train.getPlayer();
		int playernumber = p.getPlayerNumber();
		
		for (Resource r : context.getReplayingGame().getPlayerManager().getAllPlayers().get(playernumber - 1).getResources()){
			if (r instanceof Train){
				Train t = (Train) r;
				if (t.getName().equals(train.getName()) && !t.isDeployed()){
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
        
        //Refresh displayed inventories
        context.getGameScreen().getResourceController().drawPlayerResources(context.getGameLogic().getPlayerManager().getCurrentPlayer());

	}

	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Place Train Action, placing " + train.toString() + " at " + station.toString() + super.toString();
	}

}
