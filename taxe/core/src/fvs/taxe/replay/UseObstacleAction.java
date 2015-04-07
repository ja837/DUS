package fvs.taxe.replay;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;

import fvs.taxe.actor.TrainActor;
import fvs.taxe.controller.Context;
import fvs.taxe.controller.StationController;
import fvs.taxe.controller.TrainController;
import fvs.taxe.controller.TrainMoveController;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.Connection;
import gameLogic.map.Station;
import gameLogic.player.Player;
import gameLogic.resource.Obstacle;
import gameLogic.resource.Resource;
import gameLogic.resource.Skip;
import gameLogic.resource.Train;

public class UseObstacleAction extends Action {
	
	


	Connection connection;

	public UseObstacleAction(Context context, long timestamp, Connection c) {
		super(context, timestamp);
		this.connection = c;

	}

	@Override
	public void play() {
		System.out.println("Replaying an obstacle use action.");
		
		Connection c = context.getGameLogic().getMap().getConnection(connection.getStation1(), connection.getStation2());

		//c.setBlocked(5);
		
		Player currentPlayer = context.getGameLogic().getPlayerManager().getCurrentPlayer();
		
		for (Resource r : currentPlayer.getResources()){
			if (r instanceof Obstacle){
				currentPlayer.removeResource(r);
			}
		}

		
	}

	@Override
	public String toString() {
		
		return "Obstacle Use Action, placing an obstacle between  " + connection.getStation1().toString() + " and " + connection.getStation2().toString() + super.toString();
	}

}
