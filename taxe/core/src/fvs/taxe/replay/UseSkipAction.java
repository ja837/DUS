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
import gameLogic.resource.Resource;
import gameLogic.resource.Skip;
import gameLogic.resource.Train;

public class UseSkipAction extends Action {
	
	
	int player;
	Player currentPlayer;
	Skip skip;

	public UseSkipAction(Context context, long timestamp, Player playerThatUsedPowerup, Skip skipResource, int playerToMissTurn) {
		super(context, timestamp);
		currentPlayer = playerThatUsedPowerup;
		player = playerToMissTurn;
		skip = skipResource;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an skip use action.");

		context.getGameLogic().getPlayerManager().getAllPlayers().get(player).setSkip(true);
		
		//Removes the resource after it has been used
		currentPlayer.removeResource(skip);
		
	}

	@Override
	public String toString() {
		
		return "Skip Use Action, skipping turn of  " + context.getGameLogic().getPlayerManager().getAllPlayers().get(player).toString() + super.toString();
	}

}
