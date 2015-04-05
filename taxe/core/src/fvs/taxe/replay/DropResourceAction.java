package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.player.Player;
import gameLogic.resource.Resource;

public class DropResourceAction extends Action {
	
	int playerDropping;
	Resource resource;

	public DropResourceAction(Context context, long timestamp, int p, Resource r) {
		super(context, timestamp);
		playerDropping = p;
		resource = r;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an resource dropping action.");

		Player player = context.getGameLogic().getPlayerManager().getAllPlayers().get(playerDropping - 1);
		player.removeResource(resource);
		
	}

	@Override
	public String toString() {
		
		return "Resource Drop Action, removing " + resource.toString() + " from Player " + playerDropping + super.toString();
	}

}
