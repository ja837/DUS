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
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

public class UseEngineerAction extends Action {
	
	
	Player currentPlayer;
	Engineer engineer;

	public UseEngineerAction(Context context, long timestamp, Player currentPlayer, Engineer engineer) {
		super(context, timestamp);
		this.currentPlayer = currentPlayer;
		this.engineer = engineer;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an engineer use action.");

		//If the connection is blocked then it removes the blockage
		engineer.use(context.getGameLogic().getMap().getConnection(engineer.getStation1(), engineer.getStation2()));
		currentPlayer.removeResource(engineer);
	}

	@Override
	public String toString() {
		
		return "Engineer use Action, fixing the route between " + engineer.getStation1().toString() + " and " + engineer.getStation2() + " used by " + currentPlayer.toString() + super.toString();
	}

}
