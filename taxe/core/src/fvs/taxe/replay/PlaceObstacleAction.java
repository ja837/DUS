package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.map.Connection;

public class PlaceObstacleAction extends Action {
	

	Connection connection;

	public PlaceObstacleAction(Context context, long timestamp, Connection c) {
		super(context, timestamp);
		this.connection = c;

	}

	@Override
	public void play() {
		System.out.println("Replaying an obstacle use action.");
		
		//Get the connection to be blocked from the replay map
		Connection c = context.getGameLogic().getMap().getConnection(connection.getStation1(), connection.getStation2());

		
		//Block the connection
		c.setBlocked(5);
		

		
	}

	
	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Obstacle Place Action, placing an obstacle between  " + connection.getStation1().toString() + " and " + connection.getStation2().toString() + super.toString();
	}

}
