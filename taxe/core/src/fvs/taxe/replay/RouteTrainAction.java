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
		System.out.println("Replaying an train allocation action.");

		train.setRoute(route);
		TrainMoveController move = new TrainMoveController(context, train);
	}

	@Override
	public String toString() {
		
		return "Route Train Action, routing " + train.toString() + " from " + route.get(0).toString() + " to " + route.get(route.size() - 1).toString() + super.toString();
	}

}
