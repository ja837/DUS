package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.map.Connection;
import gameLogic.player.Player;
import gameLogic.resource.Modifier;
import gameLogic.resource.Resource;

public class UseModifierRemoveAction extends Action {
	
	
	Modifier modifier;

	public UseModifierRemoveAction(Context context, long timestamp, Modifier m) {
		super(context, timestamp);
		modifier = m;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an modifier use action.");

		Connection c = context.getGameLogic().getMap().getConnection(modifier.getStation1(),modifier.getStation2());
		context.getGameLogic().getMap().removeConnection(c);
		
		//The modifiers is removed from the player's inventory as it has been used
		Player currentPlayer = context.getGameLogic().getPlayerManager().getCurrentPlayer();
		
		Modifier m = null;
		
		for (Resource r : currentPlayer.getResources()){
			if (r instanceof Modifier){
				m = (Modifier) r;
				
			}
		}
		
		currentPlayer.removeResource(m);
	}

	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Modifier use (remove) Action, removing connction between " + modifier.getStation1() + " and " + modifier.getStation2() + super.toString();
	}

}
