package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import fvs.taxe.controller.TrainMoveController;
import gameLogic.map.Station;
import gameLogic.player.Player;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

import java.util.List;

public class RouteTrainAction extends Action {
	
	
	Train train;
	List<Station> route;

	public RouteTrainAction(Context context, long timestamp, Train t, List<Station> route) {
		super(context, timestamp);
		train  = t;
		this.route = route;
	}

	@Override
	public void play() {
		System.out.println("Replaying an train routing action.");
		
		//Find the player and then the train that is to be routed.
		Player p = train.getPlayer();
		int playernumber = p.getPlayerNumber();
		
		for (Resource r : context.getReplayingGame().getPlayerManager().getAllPlayers().get(playernumber - 1).getResources()){
			if (r instanceof Train){
				Train t = (Train) r;
				if (t.getName().equals(train.getName()) && t.isDeployed()){
					train = t;
				}
			}
		}

		//Give the train that route.
		train.setRoute(route);
		train.setReplay(true);
		TrainMoveController move = new TrainMoveController(context, train);
	}

	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Route Train Action, routing " + train.toString() + " from " + route.get(0).toString() + " to " + route.get(route.size() - 1).toString() + super.toString();
	}

}
