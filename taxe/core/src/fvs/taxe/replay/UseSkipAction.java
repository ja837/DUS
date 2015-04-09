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
import gameLogic.map.Station;
import gameLogic.player.Player;
import gameLogic.resource.Modifier;
import gameLogic.resource.Resource;
import gameLogic.resource.Skip;
import gameLogic.resource.Train;

public class UseSkipAction extends Action {
	
	
	int player;


	public UseSkipAction(Context context, long timestamp, int playerToMissTurn) {
		super(context, timestamp);

		player = playerToMissTurn;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an skip use action.");

		context.getGameLogic().getPlayerManager().getAllPlayers().get(player).setSkip(true);
		
		//Removes the resource after it has been used
		Player currentPlayer = context.getGameLogic().getPlayerManager().getCurrentPlayer();
		
		Skip s = null;
		
		for (Resource r : currentPlayer.getResources()){
			if (r instanceof Skip){
				s = (Skip) r;
				
			}
		}
		
		currentPlayer.removeResource(s);
	}

	@Override
	public String toString() {
		
		return "Skip Use Action, skipping turn of  " + context.getGameLogic().getPlayerManager().getAllPlayers().get(player).toString() + super.toString();
	}

}
