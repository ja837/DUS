package fvs.taxe.replay;

import java.util.List;

import com.badlogic.gdx.Gdx;

import fvs.taxe.actor.TrainActor;
import fvs.taxe.controller.Context;
import fvs.taxe.controller.StationController;
import fvs.taxe.controller.TrainController;
import fvs.taxe.controller.TrainMoveController;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.Station;
import gameLogic.player.Player;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

public class RouteTrainAction extends Action {
	
	
	Train train;
	List<Station> route;

	public RouteTrainAction(Context context, long timestamp, Train t, List<Station> route) {
		super(context, timestamp);
		train  = t;
		this.route = route;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an train routing action.");
		
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

		train.setRoute(route);
		train.setReplay(true);
		TrainMoveController move = new TrainMoveController(context, train);
	}

	@Override
	public String toString() {
		
		return "Route Train Action, routing " + train.toString() + " from " + route.get(0).toString() + " to " + route.get(route.size() - 1).toString() + super.toString();
	}

}
