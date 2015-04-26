package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.player.Player;
import gameLogic.resource.Resource;
import gameLogic.resource.Skip;

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

		//Skip the turn of the other player
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

	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Skip Use Action, skipping turn of  " + context.getGameLogic().getPlayerManager().getAllPlayers().get(player).toString() + super.toString();
	}

}
