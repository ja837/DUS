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
import gameLogic.resource.Engineer;
import gameLogic.resource.Modifier;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

public class UseEngineerAction extends Action {
	
	

	Engineer engineer;

	public UseEngineerAction(Context context, long timestamp, Engineer engineer) {
		super(context, timestamp);

		this.engineer = engineer;
	}

	@Override
	public void play() {
		System.out.println("Replaying an engineer use action.");

		//If the connection is blocked then it removes the blockage
		engineer.use(context.getGameLogic().getMap().getConnection(engineer.getStation1(), engineer.getStation2()));
		Player currentPlayer = context.getGameLogic().getPlayerManager().getCurrentPlayer();
		
		//Find the resource in the play's inventory and remove it.
		Engineer e = null;
		for (Resource r : currentPlayer.getResources()){
			if (r instanceof Engineer){
				e = (Engineer) r;
				
			}
		}
		
		currentPlayer.removeResource(e);
	}

	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Engineer use Action, fixing the route between " + engineer.getStation1().toString() + " and " + engineer.getStation2() + 
				" used by " + context.getGameLogic().getPlayerManager().getCurrentPlayer().toString() + super.toString();
	}

}
