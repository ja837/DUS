package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.map.Connection;
import gameLogic.player.Player;
import gameLogic.resource.Obstacle;
import gameLogic.resource.Resource;

public class UseObstacleAction extends Action {
	
	


	Connection connection;

	public UseObstacleAction(Context context, long timestamp, Connection c) {
		super(context, timestamp);
		this.connection = c;

	}

	@Override
	public void play() {
		System.out.println("Replaying an obstacle use action.");
		
		//Find the connection to block and block it
		Connection c = context.getGameLogic().getMap().getConnection(connection.getStation1(), connection.getStation2());
		c.setBlocked(5);
		
		//Find and remove the resource from the player
		Player currentPlayer = context.getGameLogic().getPlayerManager().getCurrentPlayer();
		
		Obstacle o = null;
		
		for (Resource r : currentPlayer.getResources()){
			if (r instanceof Obstacle){
				o = (Obstacle) r;
			}
		}

		currentPlayer.removeResource(o);
		
	}

	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Obstacle Use Action, placing an obstacle between  " + connection.getStation1().toString() + " and " + connection.getStation2().toString() + super.toString();
	}

}
